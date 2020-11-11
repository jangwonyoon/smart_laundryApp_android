import pymysql
import pandas as pd

'''
laundry_db = pymysql.connect(
    user='edit0',
    password='whdtjf1q!',
    host='112.175.184.88',
    db='edit0',
    charset='utf8'
)
'''
laundry_db = pymysql.connect(host="112.175.184.88",user="edit0",password="whdtjf1q!",db="edit0")

cur = laundry_db.cursor(pymysql.cursors.DictCursor)

sql = "SELECT * FROM 'order_record'"
cursor.execute(sql)
result = cursor.fetchall()

result = pd.DataFrame(result)
result