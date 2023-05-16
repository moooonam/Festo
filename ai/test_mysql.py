from fastapi import FastAPI, Depends
from db import SessionLocal
from model import BoothTable, FestivalTable, MemberTable, OrderLineTable, OrdersTable, ProductTable
import pandas as pd
from pydantic import BaseModel
from surprise import Dataset, Reader
from surprise import SVD
from surprise.model_selection import train_test_split

# import mysql.connector
# from sqlalchemy.ext.serializer import serialize

app = FastAPI()

# mysql_config = {
#     'host': 'k8c106.p.ssafy.io',
#     'port': 3306,
#     'user': 'root',
#     'password': '1234',
#     'database': 'festo'
# }
# mysql_conn = mysql.connector.connect(**mysql_config)


# Dependency
def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# @app.get("/db")
# def get_all_data(db: SessionLocal = Depends(get_db)):
#     return db.query(UserTable).all()

@app.get("/db/booth_data")
def get_all_data(db: SessionLocal = Depends(get_db)):
    Booths = db.query(BoothTable).all()
    for booth in Booths:
        print(booth.booth_id)
    return db.query(BoothTable).all()

@app.get("/db/festival_data")
def get_all_data(db: SessionLocal = Depends(get_db)):
    return db.query(FestivalTable).all()

@app.get("/db/member_data")
def get_all_data(db: SessionLocal = Depends(get_db)):
    return db.query(MemberTable).all()

@app.get("/db/orderline_data")
def get_all_data(db: SessionLocal = Depends(get_db)):
    return db.query(OrderLineTable).all()

@app.get("/db/orders_data")
def get_all_data(db: SessionLocal = Depends(get_db)):
    return db.query(OrdersTable).all()

@app.get("/db/product_data")
def get_all_data(db: SessionLocal = Depends(get_db)):
    products = db.query(ProductTable).all()
    # print(products)
    # for product in products:
    #     print(product)
    return db.query(ProductTable).all()

# 축제별 유저 전체 데이터
@app.get("/db/festival_userData/{festival_id}/")
def get_all_data(festival_id: str, db: SessionLocal = Depends(get_db)):
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

            # user_id, menu_id, quantity
            # user_dict.append({
            #     "user_id": orderer_id,
            #     "menu_id": line.menu_id,
            #     "count": line.quantity})
    print(user_dict)
    print(pd.DataFrame({"user_id": k[0], "menu_id": k[1], "count": v} for k, v in user_dict.items()))
    user_list = [{"user_id": k[0], "menu_id": k[1], "count": v} for k, v in user_dict.items()]
    return user_list




















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


@app.get("/ai/recommend_booth/{festival_id}/{user_number}/")
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
@app.post("/ai/order/recommend/{festival_id}/")
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
    


