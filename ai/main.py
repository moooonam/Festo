from fastapi import FastAPI, Depends
from db import SessionLocal
from model import BoothTable, FestivalTable, MemberTable, OrderLineTable, OrdersTable, ProductTable
import json
import random
import pandas as pd
from typing import Union
from pydantic import BaseModel
from surprise import Dataset, Reader
from surprise import SVD
from surprise.model_selection import train_test_split
from starlette.middleware.cors import CORSMiddleware

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ----------API 정의------------
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# 축제별 유저 전체 데이터
def UserData(festival_id: str, db: SessionLocal = Depends(get_db)):
    user_dict = {}
    booth_in_festival = db.query(BoothTable).filter(BoothTable.festival_id==festival_id).all()
    
    # 현재 지정한 축제에 있는 booth 목록
    booths_list = list(map(lambda x: x.booth_id, list(booth_in_festival)))
    Orders = db.query(OrdersTable).all()
    Orders = list(filter(lambda x: x.booth_id in booths_list,Orders))

    for order in Orders:
        orderer_id = order.orderer_id
        for line in db.query(OrderLineTable).filter(OrderLineTable.order_number == order.order_id).all():
            user_id = orderer_id
            menu_id = line.menu_id
            count = line.quantity
            
            # 중복되는 (user_id, menu_id)가 있는 경우 count를 더해줌
            if (user_id, menu_id) in user_dict:
                user_dict[(user_id, menu_id)] += count
            else:
                user_dict[(user_id, menu_id)] = count

    user_list = [{"user_id": k[0], "menu_id": k[1], "count": v} for k, v in user_dict.items()]
    return user_list

# 부스별 메뉴 데이터
def BoothData(festival_id: str, db: SessionLocal = Depends(get_db)):
    store_data = []
    Booths = db.query(BoothTable).filter(BoothTable.festival_id == festival_id).all()
    Products = db.query(ProductTable).all()
    
    for booth in Booths:
        store_data.append({
            'booth_id': booth.booth_id,
            'menu':[]
        })
    
    for product in Products:
        for booth in store_data:
            if product.booth_id == booth['booth_id']:
                booth['menu'].append(product.product_id)
    return store_data


# SVD model training
def training_model(booth_data,user_data):
    data = []
    max_score = 1
    for user_info in user_data:
        user_id = user_info['user_id']
        menu_id = user_info['menu_id']
        count = user_info['count']

        for booth_info in booth_data:
            booth_id = booth_info['booth_id']
            if menu_id in booth_info['menu']:
                data.append({'user_id': user_id, 'booth_id': booth_id, 'rating': count})
                if count > max_score:
                    max_score = count

    df = pd.DataFrame(data)

    # Surprise 데이터셋 생성
    reader = Reader(rating_scale=(0, max_score))
    dataset = Dataset.load_from_df(df, reader)

    # 학습을 위한 데이터셋 분할
    trainset, testset = train_test_split(dataset, test_size=0.2, random_state=42)

    # SVD 모델 초기화
    model = SVD(n_factors=50, n_epochs=20, random_state=42)

    # 학습
    model.fit(trainset)
    return (df, model)


# 부스 추천
@app.get("/recommend_booth/{festival_id}/{user_number}")
def recommend_booth(festival_id: int, user_number:int, db: SessionLocal = Depends(get_db)):
    booth_data = BoothData(festival_id=festival_id, db=db)
    user_data = UserData(festival_id=festival_id, db=db)

    df, model = training_model(booth_data,user_data)
    # 상위 3개 추천
    top_n = {}
    for user_id in set(df['user_id']):
        # 사용자가 이미 구매했거나 평가한 가게 리스트
        booth_list = set(df[df['user_id'] == user_id]['booth_id'])

        # 추천 대상이 되는 가게 리스트
        candidate_booths = set(df['booth_id']) - booth_list

        # 각 가게에 대한 예측 평점 계산
        predictions = []
        for booth_id in candidate_booths:
            prediction = model.predict(user_id, booth_id)
            predictions.append((booth_id, prediction.est))

        # 예측 평점이 높은 순으로 상위 3개 가게 추천
        top_n[user_id] = sorted(predictions, key=lambda x: x[1], reverse=True)[:3]

    booths = []
    try:
        booth_idxs = list(map(lambda x: x[0],top_n[user_number]))

        for idx in booth_idxs:
            Booth = db.query(BoothTable).filter(BoothTable.booth_id == idx).all()[0]
            booths.append({
                "booth_id" : Booth.booth_id,
                "booth_description":Booth.booth_description,
                "image_url":Booth.image_url,
                "location_description":Booth.location_description,
                'name':Booth.name,
            })
    except:
        pass

    # 추천해줄 부스가 없는 경우
    if len(booths) == 0:
        Booths_idxs = list(map(lambda x: x.booth_id,db.query(BoothTable).filter(BoothTable.festival_id == festival_id).all()))
        print(Booths_idxs)

        Orders_boothIdxs = list(map(lambda x: x.booth_id,db.query(OrdersTable).all()))
        set_idxs = list(set(Orders_boothIdxs))
        set_idxs = sorted(set_idxs, key=lambda x: Orders_boothIdxs.count(x), reverse=True)
        
        for idx in set_idxs:
            if idx in Booths_idxs:
                Booth = db.query(BoothTable).filter(BoothTable.booth_id == idx).all()[0]
                booths.append({
                    "booth_id" : Booth.booth_id,
                    "booth_description":Booth.booth_description,
                    "image_url":Booth.image_url,
                    "location_description":Booth.location_description,
                    'name':Booth.name,
                }) 
                if len(booths) == 2:
                    break

    return booths


class Order(BaseModel):
    boothId : int
    orderList : list


# 같은 가게 메뉴 추천
@app.post("/recommend_order/{festival_id}")
def recommend_order(festival_id: str, order:Order, db: SessionLocal = Depends(get_db)):
    booth_data = BoothData(festival_id=festival_id, db=db)
    user_data = UserData(festival_id=festival_id, db=db)

    df, model = training_model(booth_data,user_data)
  
    sorted_list = []
    try:
        # 같은 부스에 있는 메뉴
        booth_products = db.query(ProductTable).filter(ProductTable.booth_id==order.boothId).all()
        booth_menu_ids = [p.product_id for p in booth_products]

        # 같은 부스에 있는 메뉴만 candidate_list에 추가
        candidate_list = [menu_id for menu_id in booth_menu_ids if menu_id not in [item["product_id"] for item in order.orderList]]

        # 각 메뉴에 대한 예측 평점 계산
        totalCnt = sum(item["cnt"] for item in order.orderList)
        predictions = {}


        for item in order.orderList:
            uid = str(item["product_id"])
            menuCnt = item["cnt"]
            for menu in candidate_list:
                if menu in predictions:
                    predictions[menu] += model.predict(uid, menu).est * menuCnt
                else:
                    predictions[menu] = model.predict(uid, menu).est * menuCnt
        # 상위 3개 예측 메뉴 추천
        sorted_keys = sorted(predictions, key=lambda x: predictions[x], reverse=True)[:3]
        sorted_products = [k for k, v in predictions.items() if k in sorted_keys]

        for idx in sorted_products:
            product = db.query(ProductTable).filter(ProductTable.product_id == idx).all()[0]
            sorted_list.append({
                "product_id" : product.product_id,
                "image_url":product.image_url,
                "name":product.name,
                "price":product.price,
                'name':product.name,
            })
    except:
        pass

    return sorted_list

# 부스 데이터

def get_store_data(booth_id: str, db: SessionLocal = Depends(get_db)):
    store_data = []
    Booths = db.query(BoothTable).filter(BoothTable.booth_id==booth_id).all()
    Products = db.query(ProductTable).all()
    try:
        for booth in Booths:
            store_data.append({
                'booth_id': booth.booth_id,
                'booth_name':booth.name,
                'booth_image_url':booth.image_url,
                # "popular_menu":"",
                'menu':[],
                "daily_sales":[]
            })

        for product in Products:
            for booth in store_data:
                if product.booth_id == booth['booth_id']:
                    booth['menu'].append({
                        'menu_id': product.product_id,
                        'name': product.name,
                        'image_url':product.image_url,
                        'price':product.price,
                        'count':0,
                        "amount":0
                    })
        return store_data[0]
    except:
        return store_data

# 유사한 부스 제공
def CF_booth(target_booth_id, df, model):

    # 특정 부스를 이용한 사용자들의 활동 가져오기
    users_with_target_booth = df[df['booth_id'] == int(target_booth_id)]['user_id'].unique()
    
    # 추천할 부스를 모든 부스 중에서 선택하기
    all_booths = df['booth_id'].unique()
    recommendation_candidates = [booth_id for booth_id in all_booths if booth_id != int(target_booth_id)]

    # 추천할 부스를 예측하기
    predictions = []
    for user_id in users_with_target_booth:
        for booth_id in recommendation_candidates:
        # for booth_id in list(all_booths):
            prediction = model.predict(user_id, booth_id)
            predictions.append((booth_id, prediction.est))

    # 추천할 부스를 평점 기준으로 정렬
    predictions.sort(key=lambda x: x[1], reverse=True)

    # 상위 N개의 추천 부스 출력
    top_n = 5
    recommended_booths = [booth_id for booth_id, _ in predictions[:top_n]]
    return list(set(recommended_booths))
    



# 부스 데이터 제공
@app.get("/booths/{booth_id}/sales")
def booth_sales(booth_id:str, db: SessionLocal = Depends(get_db)):

    # festivalId 축제의 boothId 부스 orders
    Orders = db.query(OrdersTable).filter(OrdersTable.booth_id==booth_id).all()
    
    # orders_list => booth 주문 내역
    orders_list = [] 
    for order in Orders:
        order_dic = {
            "order_number": order.order_number,
            "status": order.status,
            "total_amounts": order.total_amounts,
            "order_id": order.order_id,
            "order_time": order.order_time,
            "order_menu":[],
        }

        order_list = []
        for line in db.query(OrderLineTable).filter(OrderLineTable.order_number == order.order_id).all():
            order_list.append(line)
            
            order_dic["order_menu"].append(
                    {
                    "menu_id": line.menu_id,
                    "quantity": line.quantity,
                    "price": line.price,
                    "amounts": line.amounts,
                    "line_idx": line.line_idx
                }
            )
        orders_list.append(order_dic)


    # 메뉴별 데이터
    required_data = get_store_data(booth_id=booth_id, db=db)
    try:
        required_data_menu = required_data["menu"]
        for order in orders_list:
            for menu_dic in order["order_menu"]:
                for menu in required_data_menu:
                    if menu["menu_id"] == menu_dic["menu_id"]:
                        menu["count"] += menu_dic["quantity"]
                        
        # 매출 많은순 정렬
        for menu in required_data_menu:
            menu["amount"] = menu["price"]*menu["count"]
        required_data_menu = sorted(required_data_menu,key=lambda x: x["amount"], reverse=True)
        
        required_data["menu"] = required_data_menu

        # 일별 매출
        daily_sales = []
        for order in Orders:
            write = True
            date = f"{order.order_time.year}-{order.order_time.month}-{order.order_time.day}"
            for sales in daily_sales:
                if sales["date"] == date:
                    sales["count"] += 1
                    sales["amount"] += order.total_amounts
                    write = False
                    break
            
            if write:
                daily_sales.append({
                    "date":date,
                    "count":1,
                    "amount": order.total_amounts
                })
        required_data["daily_sales"] = daily_sales
    except:
        return "없는 부스입니다."
    # 부스 이용자들에게 추천하는 부스의 인기메뉴 제공
    try:
        festivalId = db.query(BoothTable).filter(BoothTable.booth_id==booth_id).first().festival_id

        booth_data = BoothData(festival_id=festivalId, db=db)
        user_data = UserData(festival_id=festivalId, db=db)

        df, model = training_model(booth_data,user_data)
        CF_booths = CF_booth(booth_id, df, model)

        food_idxs = []
        for boothId in CF_booths:
            foods = db.query(ProductTable).filter(ProductTable.booth_id == boothId).all()
            for food in foods:
                food_idxs.append(food.product_id)


        food_dic = {}
        for food_idx in food_idxs:
            cnt = 0
            food_orders = db.query(OrderLineTable).filter(OrderLineTable.menu_id == food_idx).all()
            for order in food_orders:
                cnt += order.quantity

            food_dic[f'{food_idx}'] = cnt
        
        sorted_menu = sorted(food_dic, key=lambda x: int(x[0]), reverse=True)
        if len(sorted_menu) > 3:
            sorted_menu = sorted_menu[:3]
        
        required_data['insight_data'] = []
        for idx in sorted_menu:
            product = db.query(ProductTable).filter(ProductTable.product_id == idx).first()
            required_data['insight_data'].append({
                'name' : product.name,
                'image_url': product.image_url
            })
    except:
        required_data['insight_data'] = []

    return required_data



# 축제 데이터 분석
@app.get("/festivals/{festival_id}/sales")
def festival_data(festival_id:str, db: SessionLocal = Depends(get_db)):

    Booths = db.query(BoothTable).filter(BoothTable.festival_id == festival_id).all()

    festival_Booths = []
    for booth in Booths:
        festival_Booths.append(booth_sales(booth_id=booth.booth_id,db=db))

    required_data = {
        "daily_sales": [],
        "booth_data": []
    }

    daily_sales = []

    # 축제 일별 매출
    for booth in festival_Booths:
        for date_sales in booth["daily_sales"]:
            date = date_sales["date"]
            for sales in daily_sales:
                if sales["date"] == date:
                    sales["count"] += date_sales["count"]
                    sales["amount"] += date_sales["amount"]
                    break
            else:
                daily_sales.append(date_sales)
    daily_sales.sort(key=lambda x: x['date'])
    required_data["daily_sales"] = daily_sales

    # 부스별 매출
    sales_Booths = []
    for booth in Booths:
        read_booth = booth_sales(booth_id=booth.booth_id,db=db)
        dic = {
            "booth_id": read_booth["booth_id"],
            "booth_name": read_booth["booth_name"],
            "image_url": read_booth["booth_image_url"],
            "amount": 0,
            "count": 0,
        }

        for menu in read_booth["menu"]:
            dic["amount"] += menu["amount"]
            dic["count"] += menu["count"]
        sales_Booths.append(dic)

    required_data["booth_data"] = sales_Booths
    return required_data
