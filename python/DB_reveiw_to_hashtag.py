import csv
import pandas as pd
import sys
import numpy as np
import string

#df_1 = pd.read_csv('C:/py36/형제컴퓨터리뷰.csv')    #형제컴퓨터세탁
#df_2 = pd.read_csv('C:/py36/대동세탁소리뷰.csv')    #대동세탁소
#df_3 = pd.read_csv('C:/py36/현미세탁소리뷰.csv')    #현미세탁소
#df_4 = pd.read_csv('C:/py36/태양세탁소리뷰.csv')    #태양세탁소
df_5 = pd.read_csv('C:/py36/영심세탁소리뷰.csv')    #영심네수선세탁

#sys.stdout = open('C:/py36/형제컴퓨터세탁.txt','w')
#sys.stdout = open('C:/py36/대동세탁소.txt','w')
#sys.stdout = open('C:/py36/현미세탁소.txt','w')
#sys.stdout = open('C:/py36/태양세탁소.txt','w')
sys.stdout = open('C:/py36/영심네수선세탁.txt','w')

#review_1 = df_1['review']
#review_2 = df_2['review']
#review_3 = df_3['review']
#review_4 = df_4['review']
review_5 = df_5['review']

#score_1 = df_1['score']
#score_2 = df_2['score']
#score_3 = df_3['score']
#score_4 = df_4['score']
score_5 = df_5['score']

i=0

#DataFrame의 review를 가져와서 SQL문 에 넣기 -> for문으로 len(df) range설정하고 print하거나 .loc로 값 넣기

#for i in range(len(review_1)-1):
#    print("""INSERT INTO `review` VALUES(0,'""" + review_1[i] + """','',""" + str(score_1[i]) + """,'edit0000','형제컴퓨터세탁',1022100101,'메뉴명','a','b','c',1023095015);\n""")
#for i in range(len(review_2)-1):
#    print("""INSERT INTO `review` VALUES(0,'""" + review_2[i] + """','',""" + str(score_2[i]) + """,'edit0000','형제컴퓨터세탁',1022100101,'메뉴명','a','b','c',1023095015);\n""")
#for i in range(len(review_3)-1):
#    print("""INSERT INTO `review` VALUES(0,'""" + review_3[i] + """','',""" + str(score_3[i]) + """,'edit0000','형제컴퓨터세탁',1022100101,'메뉴명','a','b','c',1023095015);\n""")
#for i in range(len(review_4)-1):
#    print("""INSERT INTO `review` VALUES(0,'""" + review_4[i] + """','',""" + str(score_4[i]) + """,'edit0000','형제컴퓨터세탁',1022100101,'메뉴명','a','b','c',1023095015);\n""")
for i in range(len(review_5)-1):
    print("""INSERT INTO `review` VALUES(0,'""" + review_5[i] + """','',""" + str(score_5[i]) + """,'edit0000','형제컴퓨터세탁',1022100101,'메뉴명','a','b','c',1023095015);\n""")