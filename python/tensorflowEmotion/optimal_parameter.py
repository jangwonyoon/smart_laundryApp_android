from sklearn.model_selection import GridSearchCV
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.pipeline import Pipeline
from sklearn.linear_model import LogisticRegression
import pandas as pd

df = pd.read_csv('C:/py36/review/refined_review.csv')

X_train = df.loc[:35000,'review'].values
y_train = df.loc[:35000,'sentiment'].values
X_test = df.loc[15000:,'review'].values
y_test = df.loc[15000:,'sentiment'].values

tfidf = TfidfVectorizer(lowercase=False)

param_grid = [{'vect__ngram_range': [(1,1)], 'vect__stop__words': [stop,None], 'vect__tokenizer': [tokenizer, tokenizer_porter],'clf__penalty': ['l1','l2'], 'clf__C': [1.0, 10.0, 100.0]},{'vect__ngram_range':[(1,1)],'vect__stop__words':[stop,None],'vect__tokenizer':[tokenizer, tokenizer_porter],'vect__use__idf':[False],'vect__norm':[None],'clf__penalty':['l1','l2'],'clf__C': [1.0,10.0,100.0]}]

lr_tfidf=Pipeline([('vect',tfidf),('clf',LogisticRegression(random_state=0))])
gs_lr_tfidf = GridSearchCV(lr_tfidf,param_grid,scoring='accuracy',cv=5,verbose=1,n_jobs=-1)
gs_lr_tfidf.fit(X_train, y_train)

print('최적 파라미터 계산 종료')
print(gs_lr_tfidf.best_params_)

clf = gs_lr_tfidf.best_estimator_
print('테스트 정확도: %.3f' %clf.score(X_test,y_test))