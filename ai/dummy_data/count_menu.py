import json

# user_menu.json 파일 로드
with open('user_menu.json', 'r', encoding='utf-8') as f:
    user_menu = json.load(f)

# 각 메뉴별 주문 횟수 계산
menu_count = {}
for user in user_menu:
    for menu in user_menu[user]:
        if menu in menu_count:
            menu_count[menu] += 1
        else:
            menu_count[menu] = 1

# 결과 출력
for menu, count in menu_count.items():
    print(f'{menu}: {count}개')
