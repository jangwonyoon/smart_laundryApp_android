import csv
import pandas as pd
import sys
import numpy as np
import string
import random
import glob
import os

#지역별 data셋 불러오기

#jongro_df = pd.read_csv('C:/py36/laundry_review/data_jongro.csv')

yongsan_df = pd.read_csv('C:/py36/laundry_review/data_yongsan_final.csv')
sungdong_df = pd.read_csv('C:/py36/laundry_review/data_sungdong_final.csv')
gwangjin_df = pd.read_csv('C:/py36/laundry_review/data_gwangjin_final.csv')
dongdaemun_df = pd.read_csv('C:/py36/laundry_review/data_dongdaemun_final.csv')
junglang_df = pd.read_csv('C:/py36/laundry_review/data_junglang_final.csv')
sungbuk_df = pd.read_csv('C:/py36/laundry_review/data_sungbuk_final.csv')
gangbuk_df = pd.read_csv('C:/py36/laundry_review/data_gangbuk_final.csv')
dobong_df = pd.read_csv('C:/py36/laundry_review/data_dobong_final.csv')
nowon_df = pd.read_csv('C:/py36/laundry_review/data_nowon_final.csv')
uenpueng_df = pd.read_csv('C:/py36/laundry_review/data_uenpueng_final.csv')
sudaemun_df = pd.read_csv('C:/py36/laundry_review/data_sudaemun_final.csv')
mapo_df = pd.read_csv('C:/py36/laundry_review/data_mapo_final.csv')
yangchun_df = pd.read_csv('C:/py36/laundry_review/data_yangchun_final.csv')
gangsu_df = pd.read_csv('C:/py36/laundry_review/data_gangsu_final.csv')
guro_df = pd.read_csv('C:/py36/laundry_review/data_guro_final.csv')
gumchun_df = pd.read_csv('C:/py36/laundry_review/data_gumchun_final.csv')
yungdungpo_df = pd.read_csv('C:/py36/laundry_review/data_yungdungpo_final.csv')
dongjak_df = pd.read_csv('C:/py36/laundry_review/data_dongjak_final.csv')
gwanak_df = pd.read_csv('C:/py36/laundry_review/data_gwanak_final.csv')
seocho_df = pd.read_csv('C:/py36/laundry_review/data_seocho_final.csv')
gangnam_df = pd.read_csv('C:/py36/laundry_review/data_gangnam_final.csv')
songpa_df = pd.read_csv('C:/py36/laundry_review/data_songpa_final.csv')
gangdong_df = pd.read_csv('C:/py36/laundry_review/data_gangdong_final.csv')


#t1=len(jongro_df)
#df = jongro_df
#t1 = 1000


t2=len(yongsan_df)
t3=len(sungdong_df)
t4=len(gwangjin_df)
t5=len(dongdaemun_df)
t6=len(junglang_df)
t7=len(sungbuk_df)
t8=len(gangbuk_df)
t9=len(dobong_df)
t10=len(nowon_df)
t11=len(uenpueng_df)
t12=len(sudaemun_df)
t13=len(mapo_df)
t14=len(yangchun_df)
t15=len(gangsu_df)
t16=len(guro_df)
t17=len(gumchun_df)
t18=len(yungdungpo_df)
t19=len(dongjak_df)
t20=len(gwanak_df)
t21=len(seocho_df)
t22=len(gangnam_df)
t23=len(songpa_df)
t24=len(gangdong_df)

i=1

'''
TotalPrice = 0
price1 = 500
price2 = 1000
price3 = 1500
price4 = 2000
price5 = 2500
price6 = 3000

#데이터프레임들 생성

adf = pd.DataFrame(columns = ['Item1'])
bdf = pd.DataFrame(columns = ['Item2'])
cdf = pd.DataFrame(columns = ['Item3'])
fdf = pd.DataFrame(columns = ['Item4'])
gdf = pd.DataFrame(columns = ['Item5'])
hdf = pd.DataFrame(columns = ['Item6'])
ddf = pd.DataFrame(columns = ['Price'])
edf = pd.DataFrame(columns = ['Date'])
idf = pd.DataFrame(columns = ['Items'])

item_list = ['면세탁','드라이크리닝','운동화세탁','명품크리닝','흔적제거','정장세탁','null','null','null','null','null']
first_item_list = ['면세탁','드라이크리닝','운동화세탁','명품크리닝','흔적제거','정장세탁']
'''

#데이터 프레임들에 값 넣어주기

def datacopy(df,dft):

    TotalPrice = 0
    price1 = 500
    price2 = 1000
    price3 = 1500
    price4 = 2000
    price5 = 2500
    price6 = 3000

    #데이터프레임들 생성

    adf = pd.DataFrame(columns = ['Item1'])
    bdf = pd.DataFrame(columns = ['Item2'])
    cdf = pd.DataFrame(columns = ['Item3'])
    fdf = pd.DataFrame(columns = ['Item4'])
    gdf = pd.DataFrame(columns = ['Item5'])
    hdf = pd.DataFrame(columns = ['Item6'])
    ddf = pd.DataFrame(columns = ['Price'])
    edf = pd.DataFrame(columns = ['Date'])
    idf = pd.DataFrame(columns = ['Items'])

    item_list = ['면세탁','드라이크리닝','운동화세탁','명품크리닝','흔적제거','정장세탁','null','null','null','null','null']
    first_item_list = ['면세탁','드라이크리닝','운동화세탁','명품크리닝','흔적제거','정장세탁']

    for i in range(dft):
        year = 2020
        month = random.randint(8, 9)
        day = random.randint(1, 28)

        adf.loc[i,'Item1'] = random.choice(first_item_list)
        bdf.loc[i,'Item2'] = random.choice(item_list)
        cdf.loc[i,'Item3'] = random.choice(item_list)
        fdf.loc[i,'Item4'] = random.choice(item_list)
        gdf.loc[i,'Item5'] = random.choice(item_list)
        hdf.loc[i,'Item6'] = random.choice(item_list)
        idf.loc[i,'Items'] = (adf.loc[i,'Item1'] +',' + bdf.loc[i,'Item2'] + ',' + cdf.loc[i,'Item3'] + ',' + fdf.loc[i,'Item4'] + ',' + gdf.loc[i,'Item5'] + ',' + hdf.loc[i,'Item6'])
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

        if fdf.loc[i,'Item4'] == '면세탁':
            TotalPrice = TotalPrice + price1
        elif fdf.loc[i,'Item4'] == '드라이크리닝':
            TotalPrice = TotalPrice + price2
        elif fdf.loc[i,'Item4'] == '운동화세탁':
            TotalPrice = TotalPrice + price3
        elif fdf.loc[i,'Item4'] == '명품크리닝':
            TotalPrice = TotalPrice + price4
        elif fdf.loc[i,'Item4'] == '흔적제거':
            TotalPrice = TotalPrice + price5
        elif fdf.loc[i,'Item4'] == '정장세탁':
            TotalPrice = TotalPrice + price6
        else:
            TotalPrice = TotalPrice + 0

        if gdf.loc[i,'Item5'] == '면세탁':
            TotalPrice = TotalPrice + price1
        elif gdf.loc[i,'Item5'] == '드라이크리닝':
            TotalPrice = TotalPrice + price2
        elif gdf.loc[i,'Item5'] == '운동화세탁':
            TotalPrice = TotalPrice + price3
        elif gdf.loc[i,'Item5'] == '명품크리닝':
            TotalPrice = TotalPrice + price4
        elif gdf.loc[i,'Item5'] == '흔적제거':
            TotalPrice = TotalPrice + price5
        elif gdf.loc[i,'Item5'] == '정장세탁':
            TotalPrice = TotalPrice + price6
        else:
            TotalPrice = TotalPrice + 0
    
        if hdf.loc[i,'Item6'] == '면세탁':
            TotalPrice = TotalPrice + price1
        elif hdf.loc[i,'Item6'] == '드라이크리닝':
            TotalPrice = TotalPrice + price2
        elif hdf.loc[i,'Item6'] == '운동화세탁':
            TotalPrice = TotalPrice + price3
        elif hdf.loc[i,'Item6'] == '명품크리닝':
            TotalPrice = TotalPrice + price4
        elif hdf.loc[i,'Item6'] == '흔적제거':
            TotalPrice = TotalPrice + price5
        elif hdf.loc[i,'Item6'] == '정장세탁':
            TotalPrice = TotalPrice + price6
        else:
            TotalPrice = TotalPrice + 0

        ddf.loc[i,'Price'] = TotalPrice
        TotalPrice = 0

        if i%1000 == 1:
            print(i,",")
        else:
            continue

    print("---------------------------------------------------------------------")      #파일 구분

    #데이터프레임들을 붙이기

    df = pd.concat([df,adf], axis=1)
    df = pd.concat([df,bdf], axis=1)
    df = pd.concat([df,cdf], axis=1)
    df = pd.concat([df,fdf], axis=1)
    df = pd.concat([df,gdf], axis=1)
    df = pd.concat([df,hdf], axis=1)
    df = pd.concat([df,idf], axis=1)
    df = pd.concat([df,ddf], axis=1)
    df = pd.concat([df,edf], axis=1)

    df.index = df['사업장명']            #인덱스에 사업장명을 넣기
    final_df = df.drop(df.columns[[0]],axis='columns')      #인덱스에 사업장명을 넣었기 때문에 열에 있는 사업장명을 제거

    return final_df


ok = datacopy(yongsan_df, t2)
ok.to_csv('C:/py36/laundry_review/data_yongsan_final_2.csv',encoding='utf-8-sig')

aa = datacopy(sungdong_df, t3)
aa.to_csv('C:/py36/laundry_review/data_sungdong_final_2.csv',encoding='utf-8-sig')

bb = datacopy(gwangjin_df, t4)
bb.to_csv('C:/py36/laundry_review/data_gwangjin_final_2.csv',encoding='utf-8-sig')

cc = datacopy(dongdaemun_df, t5)
cc.to_csv('C:/py36/laundry_review/data_dongdaemun_final_2.csv',encoding='utf-8-sig')

dd = datacopy(junglang_df, t6)
dd.to_csv('C:/py36/laundry_review/data_junglang_final_2.csv',encoding='utf-8-sig')

ee = datacopy(sungbuk_df, t7)
ee.to_csv('C:/py36/laundry_review/data_sungbuk_final_2.csv',encoding='utf-8-sig')

ff = datacopy(gangbuk_df, t8)
ff.to_csv('C:/py36/laundry_review/data_gangbuk_final_2.csv',encoding='utf-8-sig')

gg = datacopy(dobong_df, t9)
gg.to_csv('C:/py36/laundry_review/data_dobong_final_2.csv',encoding='utf-8-sig')

hh = datacopy(nowon_df, t10)
hh.to_csv('C:/py36/laundry_review/data_nowon_final_2.csv',encoding='utf-8-sig')

ii = datacopy(uenpueng_df, t11)
ii.to_csv('C:/py36/laundry_review/data_uenpueng_final_2.csv',encoding='utf-8-sig')

jj = datacopy(sudaemun_df, t12)
jj.to_csv('C:/py36/laundry_review/data_sudaemun_final_2.csv',encoding='utf-8-sig')

kk = datacopy(mapo_df, t13)
kk.to_csv('C:/py36/laundry_review/data_mapo_final_2.csv',encoding='utf-8-sig')

ll = datacopy(yangchun_df, t14)
ll.to_csv('C:/py36/laundry_review/data_yangchun_final_2.csv',encoding='utf-8-sig')

mm = datacopy(gangsu_df, t15)
mm.to_csv('C:/py36/laundry_review/data_gangsu_final_2.csv',encoding='utf-8-sig')

nn = datacopy(guro_df, t16)
nn.to_csv('C:/py36/laundry_review/data_guro_final_2.csv',encoding='utf-8-sig')

oo = datacopy(gumchun_df, t17)
oo.to_csv('C:/py36/laundry_review/data_gumchun_final_2.csv',encoding='utf-8-sig')

pp = datacopy(yungdungpo_df, t18)
pp.to_csv('C:/py36/laundry_review/data_yungdungpo_final_2.csv',encoding='utf-8-sig')

qq = datacopy(dongjak_df, t19)
qq.to_csv('C:/py36/laundry_review/data_dongjak_final_2.csv',encoding='utf-8-sig')

rr = datacopy(gwanak_df, t20)
rr.to_csv('C:/py36/laundry_review/data_gwanak_final_2.csv',encoding='utf-8-sig')

ss = datacopy(seocho_df, t21)
ss.to_csv('C:/py36/laundry_review/data_seocho_final_2.csv',encoding='utf-8-sig')

tt = datacopy(gangnam_df, t22)
tt.to_csv('C:/py36/laundry_review/data_gangnam_final_2.csv',encoding='utf-8-sig')

uu = datacopy(songpa_df, t23)
uu.to_csv('C:/py36/laundry_review/data_songpa_final_2.csv',encoding='utf-8-sig')

vv = datacopy(gangdong_df, t24)
vv.to_csv('C:/py36/laundry_review/data_gangdong_final_2.csv',encoding='utf-8-sig')