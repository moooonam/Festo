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
@app.get("/db/store_data/{festival_id}/")
def get_all_data(festival_id: str, db: SessionLocal = Depends(get_db)):
    store_data = []
    Booths = db.query(BoothTable).filter(BoothTable.festival_id == festival_id).all()
    Products = db.query(ProductTable).all()
    
    for booth in Booths:
        store_data.append({
            'booth_id': booth.booth_id,
            'booth_name':booth.name,
            'booth_image_url':booth.image_url,
            'menu':[]
        })

    for product in Products:
        for booth in store_data:
            if product.booth_id == booth['booth_id']:
                booth['menu'].append({
                    'menu_id': product.product_id,
                    'name': product.name,
                    'image_url':product.image_url,
                    'price':product.price
                })
    return store_data

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


@app.get("/ai/recommend_booth/{festival_id}/{user_number}")
def get_all_data(festival_id: str, user_number:int, db: SessionLocal = Depends(get_db)):
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





# class Store(BaseModel):
#     storeName : str

# @app.post("/ai/store/analysis/")
# def dataOfBooth(store: Store):
#     print(store.storeName)

#     sales_data = {}
#     for user, stores in user_data.items():
#         for store_name, items in stores.items():
#             if store_name in store_data:
#                 if store_name not in sales_data:
#                     sales_data[store_name] = {}
#                 for item in items:
#                     if item in store_data[store_name]['menu']:
#                         if item not in sales_data[store_name]:
#                             sales_data[store_name][item] = 0
#                         sales_data[store_name][item] += 1

#     df = pd.DataFrame(sales_data).fillna(0)

#     booth_name = store.storeName

#     series = df.loc[store_data[booth_name]["menu"].keys(), booth_name]
#     sales_menu = {}
#     total_sales = 0
#     sales_menu[booth_name] = {}

#     for idx, value in series.items():
#         sales_menu[booth_name][idx] = {}
#         sales_menu[booth_name][idx]['count'] = value
#         sales_menu[booth_name][idx]['sales'] = int(value)*int(store_data[booth_name]["menu"][idx]["price"])
#         total_sales += sales_menu[booth_name][idx]['sales']

#     sales_menu[booth_name]['total_sales'] = total_sales
#     return sales_menu

# @app.get('/ai/data/festival/')
# def dataOfFestival():
#     # 가게별, 메뉴별 판매 수 계산
#     menu_sales = {}
#     for user, stores in user_data.items():
#         for store_name, items in stores.items():
#             if store_name in store_data.keys():
#                 for item in items:
#                     if item in store_data[store_name]['menu'].keys():
#                         if store_name not in menu_sales:
#                             menu_sales[store_name] = {}
#                         if item not in menu_sales[store_name]:
#                             menu_sales[store_name][item] = 0
#                         menu_sales[store_name][item] += 1

#     # 가장 많이 팔린 메뉴 출력
#     max_sales = 0
#     max_sales_store = None
#     max_sales_menu = None

#     for store, sales in menu_sales.items():
#         for menu, count in sales.items():
#             if count > max_sales:
#                 max_sales = count
#                 max_sales_store = store
#                 max_sales_menu = menu

#     print("가장 많이 팔린 메뉴는 {}의 {}입니다. ({}개 판매)".format(max_sales_store, max_sales_menu, max_sales))
#     dic = {
#         "best menu of store": max_sales_store,
#         "best menu": max_sales_menu,
#         "count of best menu": max_sales
#     }
#     return dic