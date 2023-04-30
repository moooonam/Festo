import pandas as pd
import random
import json

# data_line.csv 파일을 데이터프레임으로 변환
df = pd.read_csv('data_line.csv', encoding='utf-8')
df['가게_메뉴'] = df['가게'] + '_' + df['메뉴']

# 유저별로 10개 메뉴 선택
user_menu = {}
for i in range(1, 1001):
    user_menu[f'user_{i}'] = random.sample(list(df['가게_메뉴']), 10)

# 결과 출력
# print(user_menu)

# 딕셔너리를 JSON 파일로 저장
with open('user_menu.json', 'w', encoding='utf-8') as f:
    json.dump(user_menu, f, ensure_ascii=False, indent=4)

