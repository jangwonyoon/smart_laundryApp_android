import pymysql

db = pymysql.connect(host='112.175.184.88',
                     user='edit0',
                     passwd='whdtjf1q!',
                     db='edit0',
                     charset='utf8')

cursor = db.cursor()

sql = "UPDATE owner_temp SET data1 = '해쉬태그1', data2 = '해쉬태그2', data3 = '해쉬태그3' WHERE s_name = '태양세탁소'"

cursor.execute(sql)

cursor.execute("show tables")

db.commit()

db.close()