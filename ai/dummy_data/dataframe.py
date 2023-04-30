import pandas as pd

# data_line.csv 파일을 데이터프레임으로 변환
df = pd.read_csv('data_line.csv', encoding='utf-8')

# 데이터프레임 출력
grouped_df = df.groupby('가게').agg({'메뉴': list, '가격': list})
print(grouped_df)
