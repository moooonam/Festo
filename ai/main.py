from fastapi import FastAPI
import json
import random
import pandas as pd
from typing import Union
from pydantic import BaseModel
from surprise import Dataset, Reader
from surprise import SVD
from surprise.model_selection import train_test_split

with open("dummy_data/store.json", "r",encoding='utf-8') as f:
    store_data = json.load(f)

with open("dummy_data/user_dummy.json","r",encoding='utf-8') as f:
    user_data = json.load(f)

app = FastAPI()


@app.get("/ai/stores/")
def get_stores():
    return store_data

@app.get("/ai/users/")
def get_users():
    return user_data

@app.get('/ai/create_user/')
def create_users():
    # 랜덤한 유저 더미데이터 생성
    user_dummy = {}
    # for i in range(10):  # 10명의 유저 생성
    for i in range(100):  # 100명의 유저 생성
        user_name = f"user{i+1}"
        store_list = list(store_data.keys())  # 가게 이름 리스트
        # store_choice = random.sample(store_list, random.randint(2, 3))  # 랜덤 2~3개의 가게 선택
        store_choice = random.sample(store_list, random.randint(5, 10))  # 랜덤 5~10개의 가게 선택
        menu_dict = {}
        for store_name in store_choice:
            menu_list = list(store_data[store_name]["menu"].keys())  # 메뉴 이름 리스트
            menu_num = random.randint(1, len(menu_list))  # 랜덤 메뉴 개수 선택
            menu_choice = random.choices(menu_list, k=menu_num)  # 랜덤 메뉴 선택
            menu_dict[store_name] = menu_choice
        user_dummy[user_name] = menu_dict

    # 유저 더미데이터를 파일에 저장
    with open('dummy_data/user_dummy.json', 'w', encoding='utf-8') as f:
        json.dump(user_dummy, f, ensure_ascii=False, indent=4)
    return user_dummy


@app.get('/ai/recommend/all/')
def recommend_booths():
    # 데이터 프레임 생성
    data = []
    max_score = 1
    for user, stores in user_data.items():
        for store_name, items in stores.items():
            for item in items:
                if store_name in store_data.keys():
                    for menu_name, menu_info in store_data[store_name]['menu'].items():
                        if item == menu_name:
                            for d in data:
                                if d['user'] == user and d['item'] == item:
                                    d['rating'] += 1
                                    if d['rating'] > max_score:
                                        max_score = d['rating']
                                    break
                            else:
                                data.append({'user': user, 'item': item, 'rating': 1})

    df = pd.DataFrame(data)
    # dataframe 추출
    # print(df)

    # Surprise 데이터셋 생성
    reader = Reader(rating_scale=(0, max_score))
    dataset = Dataset.load_from_df(df, reader)

    # 학습을 위한 데이터셋 분할
    trainset, testset = train_test_split(dataset, test_size=0.2, random_state=42)

    # SVD 모델 초기화
    model = SVD(n_factors=50, n_epochs=20, random_state=42)

    # 학습
    model.fit(trainset)

    # 상위 10개 추천
    top_n = {}
    for uid in user_data:
        # uid 유저가 구매한 가게 리스트
        store_list = [store for store in user_data[uid].keys() if store in store_data]

        # uid 유저가 평가하지 않은 가게 리스트
        candidate_stores = [store for store in store_data if store not in store_list]

        # 각 가게에 대한 예측 평점 계산
        predictions = []
        for store in candidate_stores:
            prediction = model.predict(uid, store)
            predictions.append((store, prediction.est))
        
        # 예측 평점 기준 상위 3개 가게 리스트
        top_n[uid] = sorted(predictions, key=lambda x: x[1], reverse=True)[:10]
    return top_n


@app.get('/ai/recommend/{user_id}/')
def recommend_booths(user_id: str):
    # 데이터 프레임 생성
    data = []
    max_score = 1
    for user, stores in user_data.items():
        for store_name, items in stores.items():
            for item in items:
                if store_name in store_data.keys():
                    for menu_name, menu_info in store_data[store_name]['menu'].items():
                        if item == menu_name:
                            for d in data:
                                if d['user'] == user and d['item'] == item:
                                    d['rating'] += 1
                                    if d['rating'] > max_score:
                                        max_score = d['rating']
                                    break
                            else:
                                data.append({'user': user, 'item': item, 'rating': 1})

    df = pd.DataFrame(data)
    # dataframe 추출
    # print(df)

    # Surprise 데이터셋 생성
    reader = Reader(rating_scale=(0, max_score))
    dataset = Dataset.load_from_df(df, reader)

    # 학습을 위한 데이터셋 분할
    trainset, testset = train_test_split(dataset, test_size=0.2, random_state=42)

    # SVD 모델 초기화
    model = SVD(n_factors=50, n_epochs=20, random_state=42)

    # 학습
    model.fit(trainset)

    # 상위 3개 추천
    top_n = {}

    store_list = [store for store in user_data[user_id].keys() if store in store_data]

    # uid 유저가 평가하지 않은 가게 리스트
    candidate_stores = [store for store in store_data if store not in store_list]

    # 각 가게에 대한 예측 평점 계산
    predictions = []
    for store in candidate_stores:
        prediction = model.predict(user_id, store)
        predictions.append((store, prediction.est))

    # 예측 평점 기준 상위 10개 가게 리스트
    top_n[user_id] = sorted(predictions, key=lambda x: x[1], reverse=True)[:10]
    return top_n

class Order(BaseModel):
    storeName : str
    orderList : object

# 같은 가게 메뉴 추천
@app.post("/ai/order/recommend/")
def recommend_order(order:Order):
    # print(store_data[order.storeName])
    # print(order.orderList)

    # 데이터 프레임 생성
    data = []
    max_score = 1
    for user, stores in user_data.items():
        for store_name, items in stores.items():
            for item in items:
                if store_name in store_data.keys():
                    for menu_name, menu_info in store_data[store_name]['menu'].items():
                        if item == menu_name:
                            for d in data:
                                if d['user'] == user and d['item'] == item:
                                    d['rating'] += 1
                                    if d['rating'] > max_score:
                                        max_score = d['rating']
                                    break
                            else:
                                data.append({'user': user, 'item': item, 'rating': 1})

    df = pd.DataFrame(data)
    # dataframe 추출
    # print(df)

    # Surprise 데이터셋 생성
    reader = Reader(rating_scale=(0, max_score))
    dataset = Dataset.load_from_df(df, reader)

    # 학습을 위한 데이터셋 분할
    trainset, testset = train_test_split(dataset, test_size=0.2, random_state=42)

    # SVD 모델 초기화
    model = SVD(n_factors=50, n_epochs=20, random_state=42)

    # 학습
    model.fit(trainset)

    # 상위 3개 추천

    menu_list = store_data[order.storeName]['menu']

    candidate_list = [menu for menu in menu_list if menu not in order.orderList]


    # 각 메뉴에 대한 예측 평점 계산
    totalCnt = 0
    predictions = {}

    for uid in order.orderList:
        menuCnt = order.orderList[uid]
        totalCnt += menuCnt

        for menu in candidate_list:
            if predictions.get(menu):
                predictions[menu] += model.predict(uid,menu).est*menuCnt
            else:
                predictions[menu] = model.predict(uid,menu).est*menuCnt
            
    # predictions = {k: v // totalCnt for k,v in predictions.items()}
    
    sorted_keys = sorted(predictions, key=lambda x: predictions[x], reverse=True)
    sorted_list = list(map(lambda x: menu_list[x], sorted_keys))

    return sorted_list


class Store(BaseModel):
    storeName : str

@app.post("/ai/store/analysis/")
def dataOfBooth(store: Store):
    print(store.storeName)

    sales_data = {}
    for user, stores in user_data.items():
        for store_name, items in stores.items():
            if store_name in store_data:
                if store_name not in sales_data:
                    sales_data[store_name] = {}
                for item in items:
                    if item in store_data[store_name]['menu']:
                        if item not in sales_data[store_name]:
                            sales_data[store_name][item] = 0
                        sales_data[store_name][item] += 1

    df = pd.DataFrame(sales_data).fillna(0)

    booth_name = store.storeName

    series = df.loc[store_data[booth_name]["menu"].keys(), booth_name]
    sales_menu = {}
    total_sales = 0
    sales_menu[booth_name] = {}

    for idx, value in series.items():
        sales_menu[booth_name][idx] = {}
        sales_menu[booth_name][idx]['count'] = value
        sales_menu[booth_name][idx]['sales'] = int(value)*int(store_data[booth_name]["menu"][idx]["price"])
        total_sales += sales_menu[booth_name][idx]['sales']

    sales_menu[booth_name]['total_sales'] = total_sales
    return sales_menu

@app.get('/ai/data/festival/')
def dataOfFestival():
    # 가게별, 메뉴별 판매 수 계산
    menu_sales = {}
    for user, stores in user_data.items():
        for store_name, items in stores.items():
            if store_name in store_data.keys():
                for item in items:
                    if item in store_data[store_name]['menu'].keys():
                        if store_name not in menu_sales:
                            menu_sales[store_name] = {}
                        if item not in menu_sales[store_name]:
                            menu_sales[store_name][item] = 0
                        menu_sales[store_name][item] += 1

    # 가장 많이 팔린 메뉴 출력
    max_sales = 0
    max_sales_store = None
    max_sales_menu = None

    for store, sales in menu_sales.items():
        for menu, count in sales.items():
            if count > max_sales:
                max_sales = count
                max_sales_store = store
                max_sales_menu = menu

    print("가장 많이 팔린 메뉴는 {}의 {}입니다. ({}개 판매)".format(max_sales_store, max_sales_menu, max_sales))
    dic = {
        "best menu of store": max_sales_store,
        "best menu": max_sales_menu,
        "count of best menu": max_sales
    }
    return dic