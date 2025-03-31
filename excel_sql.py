import pandas as pd
import sqlite3

# 엑셀 파일 경로
excel_file_path = './example.xlsx'

# 엑셀 파일을 읽어와서 SQLite 데이터베이스에 저장
def excel_to_sqlite(excel_file_path):
    # 엑셀 파일을 읽어와서 DataFrame으로 변환
    df = pd.read_excel(excel_file_path, sheet_name=None)

    # SQLite 데이터베이스 연결
    conn = sqlite3.connect('excel_database.db')
    cursor = conn.cursor()

    # 각 시트를 테이블로 생성
    for sheet_name, sheet_df in df.items():
        # 테이블 생성
        cursor.execute(f'CREATE TABLE IF NOT EXISTS {sheet_name} ({", ".join([f"{column} TEXT" for column in sheet_df.columns])})')

        # 데이터 삽입
        for index, row in sheet_df.iterrows():
            cursor.execute(f'INSERT INTO {sheet_name} VALUES ({", ".join(["?"] * len(row))})', tuple(row))

    # 커밋 및 연결 종료
    conn.commit()
    conn.close()

# 엑셀 파일을 SQLite 데이터베이스에 저장
excel_to_sqlite(excel_file_path)

# SQLite 데이터베이스에 접근하여 조회
def query_sqlite(sheet_name, query):
    # SQLite 데이터베이스 연결
    conn = sqlite3.connect('excel_database.db')
    cursor = conn.cursor()

    # 조회
    cursor.execute(f'SELECT * FROM {sheet_name} WHERE {query}')

    # 결과 반환
    result = cursor.fetchall()
    conn.close()
    return result

# 예시 조회
sheet_name = 'Sheet1'
query = 'a = 7'
result = query_sqlite(sheet_name, query)
print(result)
