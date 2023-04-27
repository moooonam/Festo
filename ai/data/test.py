import pandas as pd

# 파일 경로와 인코딩 방식 지정
file_path = 'festival2.csv'
encoding = 'cp949'

# CSV 파일 불러오기
df = pd.read_csv(file_path, encoding=encoding)

# 데이터프레임 출력
# print(df['축제 소개'][0])

# utf-8로 인코딩하여 csv 파일로 쓰기
df.to_csv('festival_utf8.csv', index=False, encoding='utf-8')