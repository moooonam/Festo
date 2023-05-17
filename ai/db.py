from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

# MySQL 연결 설정
user_name = "root"
user_pwd = "1234"
db_host = "k8c106.p.ssafy.io"
# db_host = "http://127.0.0.1"
db_port = "3306"
db_name = "festo"
DATABASE = f"mysql+pymysql://{user_name}:{user_pwd}@{db_host}:{db_port}/{db_name}?charset=utf8mb4"

# SQLAlchemy 연결 엔진 생성
ENGINE = create_engine(DATABASE)
                      
SessionLocal = sessionmaker(
  autocommit=False,
  autoflush=False,
  bind=ENGINE
)

Base = declarative_base()

