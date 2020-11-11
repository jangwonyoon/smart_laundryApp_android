import pickle
import os
import numpy as np
import pandas as pd
from sklearn.metrics import accuracy_score

df = pd.read_csv('C:/py36/review/refined_review.csv')

X_train = df.loc[:35000, 'review'].values
y_train = df.loc[:35000, 'sentiment'].values
X_test = df.loc[15000:, 'review'].values
y_test = df.loc[15000:, 'sentiment'].values

curDir = os.getcwd()
clf = pickle.load(open(os.path.join(curDir, 'data', 'pklObject','classifier.pkl'),'rb'))

y_pred = clf.predict(X_test)
print('테스트 정확도: %.3f' %accuracy_score(y_test,y_pred))

label = {0:'부정적 의견', 1:'긍정적 의견'}

while True:
    txt = input('영문으로 리뷰를 작성하세요: ')
    if txt == '':
        break
    example = [txt]
    print('예측: %s\n확률: %.3f%%' %(label[clf.predict(example)[0]],np.max(clf.predict_proba(example))*100))
    