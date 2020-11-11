import csv
import pandas as pd
import sys
import numpy as np
import string
import random
import glob
import os

#지역별 data셋 불러오기

jongro_df = pd.read_csv('C:/py36/data_jongro_2.csv')

#t1=len(jongro_df)
df = jongro_df
t1 = 1000

i=1
j=1
t=1

index_name = []
index_name = df['사업장명'].unique()
index_name_length = len(index_name)


item_list = ['면세탁','드라이크리닝','운동화세탁','명품크리닝','흔적제거','정장세탁','null']


for i in range(t1):
    for j in range(index_name_length):
        for t in range(6):
            is_item_1 = df['Item1'] == '면세탁'
            is_item_2 = df['Item2'] == '면세탁'
            is_item_3 = df['Item3'] == '면세탁'
            is_item_4 = df['Item4'] == '면세탁'
            is_item_5 = df['Item5'] == '면세탁'
            is_item_6 = df['Item6'] == '면세탁'

    if i%100 == 1:
        print(i,",")
    else:
        continue



df.index = jongro_df['사업장명']            #인덱스에 사업장명을 넣기
final_df = df.drop(df.columns[[0]],axis='columns')      #인덱스에 사업장명을 넣었기 때문에 열에 있는 사업장명을 제거

final_df.to_csv('C:/py36/data_jongro_3.csv',encoding='utf-8-sig')

