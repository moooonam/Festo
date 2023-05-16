# coding: utf-8
from sqlalchemy import Column, Integer, String
from pydantic import BaseModel
from db import Base
from db import ENGINE


# class UserTable(Base):
#     __tablename__ = 'member'
#     id = Column(Integer, primary_key=True, autoincrement=True)
#     name = Column(String(30), nullable=True)
#     age = Column(Integer)


# class User(BaseModel):
#     id   : int
#     name : str
#     age  : int

class BoothTable(Base):
    __tablename__ = "booth"
    booth_id = Column(Integer, primary_key=True, autoincrement=True)
    booth_description = Column(String(255), nullable=True)
    status = Column(String(255))
    open_time = Column(String(255))
    close_time = Column(String(255))
    image_url = Column(String(255))
    location_description = Column(String(255))
    name = Column(String(255))
    festival_id = Column(Integer)
    owner_id = Column(Integer)

class FestivalTable(Base):
    __tablename__ = "festival"
    festival_id = Column(Integer, primary_key=True, autoincrement=True)
    description = Column(String(255), nullable=True)
    start_date = Column(String(255))
    end_date = Column(String(255))
    status = Column(String(255))
    address = Column(String(255))
    image_url = Column(String(255))
    invite_code = Column(String(255))
    name = Column(String(255))
    manager_id = Column(Integer)

class MemberTable(Base):
    __tablename__ = "member"
    id = Column(Integer, primary_key=True, autoincrement=True)
    auth_id = Column(Integer)
    nickname = Column(String(255))
    profile_image_url = Column(String(255))

class OrderLineTable(Base):
    __tablename__ = "order_line"
    order_number = Column(Integer, primary_key=True)
    amounts = Column(Integer)
    menu_id = Column(Integer)
    price = Column(Integer)
    quantity = Column(Integer)
    line_idx = Column(Integer,primary_key=True)

class OrdersTable(Base):
    __tablename__ = "orders"
    order_id = Column(Integer,primary_key=True, autoincrement=True)
    order_number = Column(Integer)
    status = Column(String(255))
    order_time = Column(String(255))
    total_amounts = Column(Integer)
    booth_id = Column(Integer)
    orderer_id = Column(Integer)
    
class ProductTable(Base):
    __tablename__ = "product"
    product_id = Column(Integer,primary_key=True, autoincrement=True)
    image_url = Column(String(255))
    name = Column(String(255))
    price = Column(Integer)
    booth_id = Column(Integer)





def main():
    # Table 없으면 생성
    Base.metadata.create_all(bind=ENGINE)

if __name__ == "__main__":
    main()