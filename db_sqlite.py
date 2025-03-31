import sqlite3

class SQLiteDB:
    def __init__(self, db_file):
        self.conn = sqlite3.connect(db_file)
        self.cursor = self.conn.cursor()

    def create_table(self, table_name, columns):
        # 테이블 생성
        column_defs = ', '.join([f'{column[0]} {column[1]}' for column in columns])
        self.cursor.execute(f'CREATE TABLE IF NOT EXISTS {table_name} ({column_defs})')
        self.conn.commit()

    def insert_data(self, table_name, data):
        # 데이터 삽입
        placeholders = ', '.join(['?'] * len(data))
        self.cursor.execute(f'INSERT INTO {table_name} VALUES ({placeholders})', data)
        self.conn.commit()

    def read_data(self, table_name, query=None):
        # 데이터 조회
        if query:
            self.cursor.execute(f'SELECT * FROM {table_name} WHERE {query}')
        else:
            self.cursor.execute(f'SELECT * FROM {table_name}')
        return self.cursor.fetchall()

    def update_data(self, table_name, set_query, where_query):
        # 데이터 수정
        self.cursor.execute(f'UPDATE {table_name} SET {set_query} WHERE {where_query}')
        self.conn.commit()

    def delete_data(self, table_name, query):
        # 데이터 삭제
        self.cursor.execute(f'DELETE FROM {table_name} WHERE {query}')
        self.conn.commit()

    def close_connection(self):
        # 연결 종료
        self.conn.close()

# 예시 사용
db_file = 'excel_database.db'
db = SQLiteDB(db_file)

# 테이블 생성
table_name = 'Sheet1'
# columns = [('id', 'INTEGER PRIMARY KEY'), ('name', 'TEXT'), ('age', 'INTEGER')]
# db.create_table(table_name, columns)

# # 데이터 삽입
# data = (1, 'John Doe', 30)
# db.insert_data(table_name, data)

# 데이터 조회
query = 'a = 4'
result = db.read_data(table_name, query)
print(result)

# # 데이터 수정
# set_query = 'name = "Jane Doe"'
# where_query = 'id = 1'
# db.update_data(table_name, set_query, where_query)

# # 데이터 삭제
# query = 'id = 1'
# db.delete_data(table_name, query)

# 연결 종료
db.close_connection()
