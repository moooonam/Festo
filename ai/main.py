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

# 유저(참여중인 축제) 데이터
@app.get("/db/personal/{festival_id}/{user_id}/")
def get_all_data(festival_id: str, user_id: str, db: SessionLocal = Depends(get_db)):
    user_list = []
    booth_in_festival = db.query(BoothTable).filter(BoothTable.festival_id==festival_id).all()
    
    # 현재 지정한 축제에 있는 booth 목록
    booths_list = list(map(lambda x: x.booth_id, list(booth_in_festival)))
    
    # 지정한 유저가 시킨 목록, 지정한 축제 booth만 전처리
    Orders = db.query(OrdersTable).filter(OrdersTable.orderer_id == user_id).all()
    Orders = list(filter(lambda x: x.booth_id in booths_list,Orders))

    # 오더를 보면서, 메뉴를 체크하되
    for order in Orders:
        for line in db.query(OrderLineTable).filter(OrderLineTable.order_number == order.order_id).all():
            user_list.append(line)
    return user_list

# festival에 속한 가게 목록
# @app.get("/db/store_data/{festival_id}/")
# def get_all_data(festival_id: str, db: SessionLocal = Depends(get_db)):
#     store_data = []
#     Booths = db.query(BoothTable).filter(BoothTable.festival_id == festival_id).all()
#     Products = db.query(ProductTable).all()
    
#     for booth in Booths:
#         store_data.append({
#             'booth_id': booth.booth_id,
#             'booth_name':booth.name,
#             'booth_image_url':booth.image_url,
#             'menu':[]
#         })

#     for product in Products:
#         for booth in store_data:
#             if product.booth_id == booth['booth_id']:
#                 booth['menu'].append({
#                     'menu_id': product.product_id,
#                     'name': product.name,
#                     'image_url':product.image_url,
#                     'price':product.price
#                 })
#     return store_data

# festival에 속한 가게 목록
@app.get("/db/store_data_Number/{festival_id}/")
def get_all_data(festival_id: str, db: SessionLocal = Depends(get_db)):
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

@app.get("/ai/recommend_booth/{festival_id}/{user_number}")
def recommend_booth(festival_id: str, user_number:int, db: SessionLocal = Depends(get_db)):
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
                "category":Booth.category,
                "image_url":Booth.image_url,
                "location_description":Booth.location_description,
                'name':Booth.name,
            })
    except:
        pass

    return booths


class Order(BaseModel):
    boothId : int
    orderList : dict


# 같은 가게 메뉴 추천
@app.post("/ai/recommend_order/{festival_id}")
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
        candidate_list = [idx for idx in booth_menu_ids if str(idx) not in list(order.orderList.keys())]
        
        # 각 메뉴에 대한 예측 평점 계산
        totalCnt = sum(order.orderList.values())
        predictions = {}
        for uid, menuCnt in order.orderList.items():
            for menu in candidate_list:
                if predictions.get(menu):
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

# festival에 속한 가게 목록

def get_store_data(booth_id: str, db: SessionLocal = Depends(get_db)):
    store_data = []
    Booths = db.query(BoothTable).filter(BoothTable.booth_id==booth_id).all()
    Products = db.query(ProductTable).all()
    
    for booth in Booths:
        store_data.append({
            'booth_id': booth.booth_id,
            'booth_name':booth.name,
            'booth_image_url':booth.image_url,
            "popular_menu":"",
            'menu':[]
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
                    'log':[]
                })
    return store_data[0]



class BoothDataRequired(BaseModel):
    boothId : int
    festivalId : int

# 부스 데이터 제공
@app.post("/ai/booth_data")
def recommend_order(boothdata: BoothDataRequired, db: SessionLocal = Depends(get_db)):

    # festivalId 축제의 boothId 부스 orders
    Orders = db.query(OrdersTable).filter(OrdersTable.booth_id==boothdata.boothId).all()
    
    # orders_list => booth 주문 내역
    orders_list = [] 
    for order in Orders:
        order_dic = {
            "order_number": order.order_number,
            "status": order.status,
            "total_amounts": order.total_amounts,
            "order_id": order.order_id,
            "order_time": order.order_time,
            "order_menu":[]
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
    required_data = get_store_data(booth_id=boothdata.boothId, db=db)
    required_data_menu = required_data["menu"]
    for order in orders_list:
        for menu_dic in order["order_menu"]:
            for menu in required_data_menu:
                if menu["menu_id"] == menu_dic["menu_id"]:
                    menu["count"] += menu_dic["quantity"]
                    menu["log"].append({
                        "order_number": order["order_number"],
                        "status":order["status"],
                        "order_id":order["order_id"],
                        "order_time":order["order_time"],
                        "quantity":menu_dic["quantity"]
                    })
    required_data["menu"] = required_data_menu

    # 인기메뉴 추가
    popular_menu = (sorted(required_data["menu"],key=lambda x: x["count"], reverse=True)[0])
    popular_menu = {
        "menu_id":popular_menu["menu_id"],
        "name":popular_menu["name"],
        "image_url":popular_menu["image_url"],
        "price":popular_menu["price"],
    }
    required_data["popular_menu"] = popular_menu


    # 추천
    # booth_data = BoothData(festival_id=boothdata.festivalId, db=db)
    # user_data = UserData(festival_id=boothdata.festivalId, db=db)

    # df, model = training_model(booth_data,user_data)
    # print(df)

    return required_data




