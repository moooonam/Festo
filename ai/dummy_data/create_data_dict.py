import csv
import json

# 파일 경로 설정
file_path = 'data_line.csv'

# 가게별 메뉴 가격 정보를 담을 딕셔너리
menu_dict = {}

# CSV 파일 열기
with open(file_path, encoding='utf-8') as file:
    reader = csv.reader(file)
    # 첫 번째 줄은 컬럼명으로 처리
    columns = next(reader)
    for row in reader:
        # CSV 파일의 각 행에서 가게 이름, 메뉴, 가격 정보 추출
        store, menu, price = row

        # 가게 이름이 딕셔너리에 없는 경우 새로운 키 생성
        if store not in menu_dict:
            menu_dict[store] = {}

        # 메뉴 이름과 가격 정보를 딕셔너리에 추가
        menu_dict[store][menu] = price

# 딕셔너리 출력
print(menu_dict)

# 딕셔너리를 JSON 파일로 저장할 경로
json_path = 'menu_dict.json'

# 딕셔너리를 JSON 형식으로 변환하여 파일에 저장
with open(json_path, 'w', encoding='utf-8') as json_file:
    json.dump(menu_dict, json_file, ensure_ascii=False, indent=4)