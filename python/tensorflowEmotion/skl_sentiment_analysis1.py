import re
import pandas as pd
from time import time

def preprocessor(text):
    text = re.sub('<[^>]*>', '', text)
    emoticons = re.findall('(?::|;|=)(?:-)?(?:\)|\(|D|P)|\^.?\^', text)
    text = re.sub('[\W]+', ' ', text.lower()) + ' '.join(emoticons).replace('-','')

    return text

df = pd.read_csv('C:/py36/review/review.csv')

stime = time()
print('전처리 시작')
df['review'] = df['review'].apply(preprocessor)
print('전처리 완료: 소요시간 [%d]초' %(time()-stime))

df.to_csv('C:/py36/review/refined_review.csv',index=False)