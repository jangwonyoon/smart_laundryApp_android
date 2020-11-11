import csv
import pandas as pd
import sys
import numpy as np
import string
import random
import matplotlib.pyplot as plt
from datetime import datetime

#data셋 만들기(count,s_address,s_name,items)

df = pd.read_csv('C:/py36/data.csv')

#t는 행 개수, i는 반복문에 쓰일 변수, dates는 날짜에 쓰일 배열

#t=65003
t=1000
i=1
dates=[]

#임의로 지정해준 item들의 가격

TotalPrice = 0
price1 = 500
price2 = 1000
price3 = 1500
price4 = 2000
price5 = 2500
price6 = 3000

#Data Frame 목록 (각각의 columns)

adf = pd.DataFrame(columns = ['Item1'])
bdf = pd.DataFrame(columns = ['Item2'])
cdf = pd.DataFrame(columns = ['Item3'])
ddf = pd.DataFrame(columns = ['Price'])
edf = pd.DataFrame(columns = ['Date'])

#내가 지정해준 item의 리스트

item_list = ['면세탁','드라이크리닝','운동화세탁','명품크리닝','흔적제거','정장세탁','null']

#행의 개수만큼 아이템1,2,3과 총가격과 날짜를 랜덤 입력

for i in range(t):
    year = 2020
    month = random.randint(8, 9)
    day = random.randint(1, 28)

    adf.loc[i,'Item1'] = random.choice(item_list)
    bdf.loc[i,'Item2'] = random.choice(item_list)
    cdf.loc[i,'Item3'] = random.choice(item_list)
    ddf.loc[i,'Price'] = 0
    edf.loc[i,'Date'] = (str(year) + '-' + str(month) + '-' + str(day))

    if adf.loc[i,'Item1'] == '면세탁':
        TotalPrice = TotalPrice + price1
    elif adf.loc[i,'Item1'] == '드라이크리닝':
        TotalPrice = TotalPrice + price2
    elif adf.loc[i,'Item1'] == '운동화세탁':
        TotalPrice = TotalPrice + price3
    elif adf.loc[i,'Item1'] == '명품크리닝':
        TotalPrice = TotalPrice + price4
    elif adf.loc[i,'Item1'] == '흔적제거':
        TotalPrice = TotalPrice + price5
    elif adf.loc[i,'Item1'] == '정장세탁':
        TotalPrice = TotalPrice + price6
    else:
        TotalPrice = TotalPrice + 0

    if bdf.loc[i,'Item2'] == '면세탁':
        TotalPrice = TotalPrice + price1
    elif bdf.loc[i,'Item2'] == '드라이크리닝':
        TotalPrice = TotalPrice + price2
    elif bdf.loc[i,'Item2'] == '운동화세탁':
        TotalPrice = TotalPrice + price3
    elif bdf.loc[i,'Item2'] == '명품크리닝':
        TotalPrice = TotalPrice + price4
    elif bdf.loc[i,'Item2'] == '흔적제거':
        TotalPrice = TotalPrice + price5
    elif bdf.loc[i,'Item2'] == '정장세탁':
        TotalPrice = TotalPrice + price6
    else:
        TotalPrice = TotalPrice + 0

    if cdf.loc[i,'Item3'] == '면세탁':
        TotalPrice = TotalPrice + price1
    elif cdf.loc[i,'Item3'] == '드라이크리닝':
        TotalPrice = TotalPrice + price2
    elif cdf.loc[i,'Item3'] == '운동화세탁':
        TotalPrice = TotalPrice + price3
    elif cdf.loc[i,'Item3'] == '명품크리닝':
        TotalPrice = TotalPrice + price4
    elif cdf.loc[i,'Item3'] == '흔적제거':
        TotalPrice = TotalPrice + price5
    elif cdf.loc[i,'Item3'] == '정장세탁':
        TotalPrice = TotalPrice + price6
    else:
        TotalPrice = TotalPrice + 0



    ddf.loc[i,'Price'] = TotalPrice
    TotalPrice = 0

    if i%100 == 1:
        print(i,",")
    else:
        continue

#Data Frame들 합치기

df = pd.concat([df,adf], axis=1)
df = pd.concat([df,bdf], axis=1)
df = pd.concat([df,cdf], axis=1)
df = pd.concat([df,ddf], axis=1)
df = pd.concat([df,edf], axis=1)

#필요한 부분만 추출하는 조건문들과 행선택, 인덱스 선택, csv파일로 저장

is_keep = df['영업상태구분코드'] == 1
seoul_df = df['소재지전체주소'].str.startswith("서울")
finall_df = df[is_keep & seoul_df]
seoul_yes_df = finall_df[['소재지전체주소','사업장명','Item1','Item2','Item3','Price','Date']]
seoul_yes_df.index = seoul_yes_df['사업장명']
final_df = seoul_yes_df.drop(seoul_yes_df.columns[[1]],axis='columns')
final_df.to_csv('C:/py36/data_start.csv',encoding='utf-8-sig')


#지역별로 csv파일 나누기

jongro_df = final_df.loc[final_df['소재지전체주소'].str.contains('종로구',na=False)]
yongsan_df = final_df.loc[final_df['소재지전체주소'].str.contains('용산구',na=False)]
sungdong_df = final_df.loc[final_df['소재지전체주소'].str.contains('성동구',na=False)]
gwangjin_df = final_df.loc[final_df['소재지전체주소'].str.contains('광진구',na=False)]
dongdaemun_df = final_df.loc[final_df['소재지전체주소'].str.contains('동대문구',na=False)]
junglang_df = final_df.loc[final_df['소재지전체주소'].str.contains('중랑구',na=False)]
sungbuk_df = final_df.loc[final_df['소재지전체주소'].str.contains('성북구',na=False)]
gangbuk_df = final_df.loc[final_df['소재지전체주소'].str.contains('강북구',na=False)]
dobong_df = final_df.loc[final_df['소재지전체주소'].str.contains('도봉구',na=False)]
nowon_df = final_df.loc[final_df['소재지전체주소'].str.contains('노원구',na=False)]
uenpueng_df = final_df.loc[final_df['소재지전체주소'].str.contains('은평구',na=False)]
sudaemun_df = final_df.loc[final_df['소재지전체주소'].str.contains('서대문구',na=False)]
mapo_df = final_df.loc[final_df['소재지전체주소'].str.contains('마포구',na=False)]
yangchun_df = final_df.loc[final_df['소재지전체주소'].str.contains('양천구',na=False)]
gangsu_df = final_df.loc[final_df['소재지전체주소'].str.contains('강서구',na=False)]
guro_df = final_df.loc[final_df['소재지전체주소'].str.contains('구로구',na=False)]
gumchun_df = final_df.loc[final_df['소재지전체주소'].str.contains('금천구',na=False)]
yungdungpo_df = final_df.loc[final_df['소재지전체주소'].str.contains('영등포구',na=False)]
dongjak_df = final_df.loc[final_df['소재지전체주소'].str.contains('동작구',na=False)]
gwanak_df = final_df.loc[final_df['소재지전체주소'].str.contains('관악구',na=False)]
seocho_df = final_df.loc[final_df['소재지전체주소'].str.contains('서초구',na=False)]
gangnam_df = final_df.loc[final_df['소재지전체주소'].str.contains('강남구',na=False)]
songpa_df = final_df.loc[final_df['소재지전체주소'].str.contains('송파구',na=False)]
gangdong_df = final_df.loc[final_df['소재지전체주소'].str.contains('강동구',na=False)]

#지역별 csv파일 저장

jongro_df.to_csv('C:/py36/data_jongro.csv',encoding='utf-8-sig')
yongsan_df.to_csv('C:/py36/data_yongsan.csv',encoding='utf-8-sig')
sungdong_df.to_csv('C:/py36/data_sungdong.csv',encoding='utf-8-sig')
gwangjin_df.to_csv('C:/py36/data_gwangjin.csv',encoding='utf-8-sig')
dongdaemun_df.to_csv('C:/py36/data_dongdaemun.csv',encoding='utf-8-sig')
junglang_df.to_csv('C:/py36/data_junglang.csv',encoding='utf-8-sig')
sungbuk_df.to_csv('C:/py36/data_sungbuk.csv',encoding='utf-8-sig')
gangbuk_df.to_csv('C:/py36/data_gangbuk.csv',encoding='utf-8-sig')
dobong_df.to_csv('C:/py36/data_dobong.csv',encoding='utf-8-sig')
nowon_df.to_csv('C:/py36/data_nowon.csv',encoding='utf-8-sig')
uenpueng_df.to_csv('C:/py36/data_uenpueng.csv',encoding='utf-8-sig')
sudaemun_df.to_csv('C:/py36/data_sudaemun.csv',encoding='utf-8-sig')
mapo_df.to_csv('C:/py36/data_mapo.csv',encoding='utf-8-sig')
yangchun_df.to_csv('C:/py36/data_yangchun.csv',encoding='utf-8-sig')
gangsu_df.to_csv('C:/py36/data_gangsu.csv',encoding='utf-8-sig')
guro_df.to_csv('C:/py36/data_guro.csv',encoding='utf-8-sig')
gumchun_df.to_csv('C:/py36/data_gumchun.csv',encoding='utf-8-sig')
yungdungpo_df.to_csv('C:/py36/data_yungdungpo.csv',encoding='utf-8-sig')
dongjak_df.to_csv('C:/py36/data_dongjak.csv',encoding='utf-8-sig')
gwanak_df.to_csv('C:/py36/data_gwanak.csv',encoding='utf-8-sig')
seocho_df.to_csv('C:/py36/data_seocho.csv',encoding='utf-8-sig')
gangnam_df.to_csv('C:/py36/data_gangnam.csv',encoding='utf-8-sig')
songpa_df.to_csv('C:/py36/data_songpa.csv',encoding='utf-8-sig')
gangdong_df.to_csv('C:/py36/data_gangdong.csv',encoding='utf-8-sig')


