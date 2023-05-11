from fastapi import FastAPI, Depends
from db import SessionLocal
from model import BoothTable, FestivalTable, MemberTable, OrderLineTable, OrdersTable, ProductTable
# import mysql.connector
# from sqlalchemy.ext.serializer import serialize

app = FastAPI()
dic = "안녕"

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


@app.get("/db/personal/{user_id}/")
def get_all_data(user_id: str, db: SessionLocal = Depends(get_db)):
    user_list = []
    OrderLine = db.query(OrderLineTable).all()
    Orders = db.query(OrdersTable).filter(OrdersTable.orderer_id == user_id).all()
    
    for order in Orders:
        print(order.order_id)
        for line in db.query(OrderLineTable).filter(OrderLineTable.order_number == order.order_id).all():
            print(line)
            user_list.append(line)

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
                    'name': product.name,
                    'image_url':product.image_url,
                    'price':product.price
                })
    return store_data



# @app.get("/health-check")
# def health_check():
#     try:
#         cursor = mysql_conn.cursor()
#         cursor.execute("SELECT 1")
#         result = cursor.fetchone()
#         cursor.close()
#         return {"message": "MySQL 연결 성공!"}
#     except Exception as e:
#         return {"message": f"MySQL 연결 실패: {str(e)}"}
    
    
# # 테이블 생성
# @app.get("/create_table")
# async def create_table():
#     cursor = mysql_conn.cursor()
#     cursor.execute("""
#         CREATE TABLE my_table (
#             id INT AUTO_INCREMENT PRIMARY KEY,
#             name VARCHAR(255) NOT NULL,
#             age INT
#         )
#     """)
#     mysql_conn.commit()
#     return {"message": "Table created successfully!"}