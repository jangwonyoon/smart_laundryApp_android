# 개요

textrank는 Michalcea(2004)이 제안한 알고리즘으로 텍스트에 관한 graph-based ranking model로써, Google의 PageRank를 활용한 알고리즘입니다.

# 목차

1. 요약  
2. PageRank  
3. TextRank
4. 적용

# 요약

![슬라이드8](https://user-images.githubusercontent.com/17975141/96361301-79ebe480-115f-11eb-828a-67f328896b29.PNG)


# PageRank

PageRank 는 가장 대표적인 graph ranking 알고리즘입니다. 
Google 의 Larry Page 가 초기 Google 의 검색 엔진의 랭킹 알고리즘으로 만든 알고리즘으로도 유명합니다. 
Web page graph 에서 중요한 pages 를 찾아서 검색 결과의 re-ranking 의 과정에서 중요한 pages 의 ranking 을 올리는데 이용되었습니다.

중요한 web pages를 찾기 위하여 PageRank는 매우 직관적인 아이디어를 이용하였습니다. 
많은 유입 링크(backlinks)를 지니는 pages 가 중요한 pages라 가정하였습니다.
각 web page가 다른 web page에게 자신의 점수 중 일부를 부여합니다. 
다른 web page로부터의 링크 (backlinks)가 많은 page는 자신에게 모인 점수가 클 것입니다. 
자신으로 유입되는 backlinks가 적은 pages는 다른 web pages로부터 받은 점수가 적을 것입니다. 
또한 모든 pages가 같은 양의 점수를 가지는 것이 아닙니다. 중요한 pages는 많은 점수를 가지고 있습니다. 
Backlinks가 적은 링크라 하더라도 중요한 page에서 투표를 받은 page 는 중요한 page가 됩니다.

PageRank에서 각 node의 중요도 PR(u)는 다음처럼 계산됩니다.
B_u는 page u의 backlinks의 출발점 마디입니다.
v에서 u로 webpage의 hyperlink가 있습니다.
각 page v는 자신의 점수를 자신이 가진 links의 개수만큼으로 나눠서 각각의 page u로 전달합니다.
page u는 page v로부터 받은 점수의 합에 상수 c를 곱합니다.
그리로 전체 마디의 개수 N의 역수인 1/N의 (1-c)배 만큼을 더합니다.
c는 [0,1]사이의 상수입니다. 논문에서는 0.85를 이용하여 저도 0.85를 이용하였습니다.

![111](https://user-images.githubusercontent.com/17975141/96290981-6be47980-1022-11eb-8f98-92336106ced4.png)

PageRank는 N개의 node가 존재하는 graph에 각 마디마다 공평하게 1/N의 점수를 줍니다.
한 step마다 모든 node의 점수는 link들을 따라 연결된 다른 node들로 이동합니다.
한 node가 두개 이상이라면 점수는 공평히 나누어져 link를 따라 이동합니다.
이 부분이 위 식의 PR(v)/N_v 입니다.
Backlinks가 많은 node에는 많은 점수가 모입니다.
이 과정을 한 번이 아닌 여러 번 수행합니다.

![image](https://user-images.githubusercontent.com/17975141/96291860-abf82c00-1023-11eb-884e-b7fbc6199e3b.png)

이런 과정을 각 마디에 존재하는 점수가 변하지 않는 시점이 생깁니다.

![image](https://user-images.githubusercontent.com/17975141/96291957-d0ec9f00-1023-11eb-8070-c735484f3a34.png)

이 때, 그래프가 Cyclic graph여야만 PageRank를 적용할 수 있습니다.
즉 다른 마디로부터 들어오는 link는 있지만 다른 마디로 가는 link가 없는 node는 있어서는 안된다는 것입니다.
이런 상황에서는 문제를 해결하기 위해 각 node에 존재하는 점수의 85%(c=0.85)만큼만 남겨두고 (1-c),15%는 임의의 노드로 보냅니다.
모든 마디에서 15%의 개미가 다른 마디로 나뉘어서 보내지기 때문에 각 마디는 (1-c)/N의 점수가 새로 유입됩니다.
이렇게 되면 PageRank의 Bias가 (1-c)/N인 Cyclic graph가 완성됩니다.
  
  
그림으로 요약해보겠습니다.
  
![슬라이드1](https://user-images.githubusercontent.com/17975141/96369969-5349a000-1197-11eb-99b9-e18de7e4e265.png)

![슬라이드2](https://user-images.githubusercontent.com/17975141/96361285-56289e80-115f-11eb-8bf1-d5fdad02b654.PNG)

![슬라이드3](https://user-images.githubusercontent.com/17975141/96361291-5cb71600-115f-11eb-9888-951a90ba7065.PNG)

![슬라이드4](https://user-images.githubusercontent.com/17975141/96361293-62acf700-115f-11eb-8a3a-6008b97379ab.PNG)

![슬라이드5](https://user-images.githubusercontent.com/17975141/96361297-693b6e80-115f-11eb-9abe-8b3915222b03.PNG)

![슬라이드6](https://user-images.githubusercontent.com/17975141/96361298-6f314f80-115f-11eb-97c1-e404227f4d2b.PNG)

![슬라이드7](https://user-images.githubusercontent.com/17975141/96361300-75273080-115f-11eb-9360-cde619d48298.PNG)


# Textrank

TextRank는 word graph나 sentence graph를 구축한 뒤, Graph ranking알고리즘인 PageRank를 이용하여 각각 키워드와 핵심 문장을 선택합니다.
TextRank는 핵심 단어를 선택하기 위해서 단어 간의 co-occurrence graph를 만듭니다. 
핵심 문장을 선택하기 위해서는 문장 간 유사도를 기반으로 sentence similarity graph를 만듭니다. 
그 뒤 각각 그래프에 PageRank를 학습하여 각 마디 (단어 혹은 문장) 의 랭킹을 계산합니다. 
이 랭킹이 높은 순서대로 키워드와 핵심 문장이 됩니다. 


```py
from collections import Counter

def scan_vocabulary(sents, tokenize, min_count=2):
  counter = Counter(w for sent in sents for w in tokenize(sent))
  counter = {w:c for w,c in counter.items() if c >= min_count}
  idx_to_vocab = [w for w, _ in sorted(counter.items(), key=lambda x:-x[1])]
  vocab_to_idx = {vocab:idx for idx, vocab in enumerate(idx_to_vocab)}
  return idx_to_vocab, vocab_to_idx
```

키워드를 추출하기 위해서 먼저 단어 그래프를 만들어야 합니다. 
마디인 단어는 주어진 문서 집합에서 최소 빈도수 min_count 이상 등장한 단어들 입니다. 
sents 는 list of str 형식의 문장들이며, tokenize 는 str 형식의 문장을 list of str 형식의 단어열로 나누는 토크나이저 입니다.
counter는 sent와 sent에 있는 단어의 개수를 체크하여 c가 min_count보다 큰 {w,c}의 형태의 딕셔너리입니다.
idx_to_vocab은 key를 x[1]로 하고 key의 값이 큰 순으로 정렬을 합니다.
idx_to_vocab: [vocab]의 리스트, list에 [idx]로 접근
vocab_to_idx는 idx_to_vocab의 값에서 idx(순서)와 vocab(단어)를 뽑아냅니다.
vocab_to_idx: {vocab: idx}의 형태의 딕셔너리.  
  
이제 그래프에 필요한 node들이 생성되었습니다.

```py
from collections import defaultdict
from scipy.sparse import csr_matrix

def dict_to_mat(d, n_rows, n_cols):
    rows, cols, data = [], [], []
    for (i, j), v in d.items():
        rows.append(i)
        cols.append(j)
        data.append(v)
    return csr_matrix((data, (rows, cols)), shape=(n_rows, n_cols))

def cooccurrence(tokens, vocab_to_idx, window=2, min_cooccurrence=2):
    counter = defaultdict(int)
    for s, tokens_i in enumerate(tokens):
        vocabs = [vocab_to_idx[w] for w in tokens_i if w in vocab_to_idx]
        n = len(vocabs)
        for i, v in enumerate(vocabs):
            if window <= 0:
                b, e = 0, n
            else:
                b = max(0, i - window)
                e = min(i + window, n)
            for j in range(b, e):
                if i == j:
                    continue
                counter[(v, vocabs[j])] += 1
                counter[(vocabs[j], v)] += 1
    counter = {k:v for k,v in counter.items() if v >= min_cooccurrence}
    n_vocabs = len(vocab_to_idx)
    return dict_to_mat(counter, n_vocabs, n_vocabs)
```
    
TextRank 에서 두 단어 간의 유사도를 정의하기 위해서는 두 단어의 co-occurrence(유사도) 를 계산해야 합니다. 
Co-occurrence 는 문장 내에서 두 단어의 간격이 window 인 횟수입니다. 논문에서는 2 ~ 8 사이의 값을 이용하기를 추천하였습니다.
window = N이라 하면, 특정 단어에서 좌,우로 N개의 단어를 참고하게 됩니다.
문장 내에 함께 등장한 모든 경우를 co-occurrence 로 정의하기 위하여 window 에 -1 을 입력합니다. 
window가 0보다 작으니 range가 0부터 단어 개수인 n이 되어 모든 단어를 검사하게 됩니다.
counter로 단어의 개수를 세고, min_coocurrence보다 큰 v의 값을 가진 단어들만 
min_coocurrence의 값은 최소 유사도로서, min_coocurrence의 값보다 작은 유사도를 가진 단어는 matrix에 포함되지 못하도록 합니다.
dict_to_mat 함수는 dict of dict 형식의 그래프를 아래와 같은 scipy의 sparse matrix(희소행렬 - 단어수 세기에 좋음)로 변환하는 함수입니다.  
  
아래 그래프 값은 co-occurrence입니다. 이 값들이 그래프의 node 값인 R이 됩니다.

![dd](https://user-images.githubusercontent.com/17975141/96014510-6f8cca80-0e81-11eb-9236-def236b11750.png)

matrix의 column과 index 사이에 값이 있다면 edge가 생깁니다. 이제 edge들이 생성되었습니다.
window값에 따라 edge의 개수가 달라집니다. window값이 크면 node의 edge의 개수가 많아집니다. 왜냐하면 window가 크면 window 값만큼 선택한 단어 주변 단어들을 검사하기 때문입니다.
그래프가 지나치게 dense(밀집)해지는 것을 방지하고 싶다면 min_coocurrence와 window값을 크게하여 그래프를 sparse(드문드문)하게 만들 수도 있습니다.    
그리하여 아래와 같은 그래프를 만들 수 있습니다.

![graph_wordgraph](https://user-images.githubusercontent.com/17975141/96010842-394d4c00-0e7d-11eb-88c1-f8ed16bc6634.png)
  


TextRank 에서는 명사, 동사, 형용사와 같은 단어만 단어 그래프를 만드는데 이용합니다. 
모든 종류의 단어를 이용하면 ‘그’, ‘이’ 와 같은 단어들이 다른 단어들과 압도적인 co-occurrence 를 지니기 때문입니다. 
없애고 싶은 단어가 있다면 stopwords 를 지정하여 키워드 후보만 그래프에 남겨둘 수 있습니다.
tokenize 함수는 불필요한 단어를 모두 걸러내고, 필요한 단어 혹은 품사만 return 하는 함수입니다.
지금은 세탁소 댓글이 완료되지 않았기 때문에 stopwords를 지정하지 않았습니다.

위 과정을 정리하면 아래와 같은 word_graph 함수를 만들 수 있습니다.

```py
def word_graph(sents, tokenize=None, min_count=2, window=2, min_cooccurrence=2):
    idx_to_vocab, vocab_to_idx = scan_vocabulary(sents, tokenize, min_count)
    tokens = [tokenize(sent) for sent in sents]
    g = cooccurrence(tokens, vocab_to_idx, window, min_cooccurrence, verbose)
    return g, idx_to_vocab
```

그 뒤 만들어진 그래프에 PageRank 를 학습하는 함수를 만듭니다. 
입력되는 x 는 co-occurrence 그래프일 수 있으니, column sum 이 1 이 되도록 L1 normalization 을 합니다. 이를 A 라 합니다. 
A * R 은 column j에서 row i로의 랭킹 R_j의 전달되는 값을 의미합니다. 
이 값에 df 를 곱하고, 모든 마디에 1 - df 를 더합니다. 신뢰도 높은 R값을 위해 이를 max_iter 만큼 반복합니다.
값이 수렴하게되면 max_iter 값이 더 높아도 랭킹 R의 값은 변하지 않습니다.

```py
import numpy as np
from sklearn.preprocessing import normalize

def pagerank(x, df=0.85, max_iter=30):
    assert 0 < df < 1

    # initialize(초기화)
    A = normalize(x, axis=0, norm='l1')
    R = np.ones(A.shape[0]).reshape(-1,1)	
    #np.ones : 다 1로 채우는 것 #A.shape[0] : A의 행 갯수 # reshape(-1,1) : 열을 1개로 두었을 때 가변적으로 만들어지는 행의 개수 -> 만약 총 12개면 -1이 12가 된다.
    bias = (1 - df) * np.ones(A.shape[0]).reshape(-1,1)

    # iteration
    for _ in range(max_iter):
        R = df * (A * R) + bias

    return R
```


이 과정을 정리하면 아래와 같은 textrank_keyword 함수를 만들 수 있습니다.

```py
def textrank_keyword(sents, tokenize, min_count, window, min_cooccurrence, df=0.85, max_iter=30, topk=30):
    g, idx_to_vocab = word_graph(sents, tokenize, min_count, window, min_cooccurrence)
    R = pagerank(g, df, max_iter).reshape(-1)	#1차원 배열로 reshape
    idxs = R.argsort()[-topk:]	#큰 순서대로 30개 정렬
    keywords = [(idx_to_vocab[idx], R[idx]) for idx in reversed(idxs)]
    return keywords
```
  
  
  

# 적용

크롤링으로 수집한 인크레더블 영화 댓글 10267개의 keyword를 구합니다.  
  
크롤링한 10267개의 댓글입니다.  
```
	|num|	ID|	review|	score
0|	1|	yski****|	 잭잭이랑 에드나 케미 미쳤닼ㅋㅋㅋ| 	10
1|	2|	배센도(bbtj****)|	 어릴 때 1편을 보고 성인이 된 올해 2편을 봤다  또 보고 싶다  3편도 나오면 좋겠다|  	10
2|  3|	space(tmd5****)|	 속편도 이리 완벽할 수 있구나 |   	10
3|	4|	황진이의두번째팬티(sion****)|	 중심히어로와 빌런이 여성이라는 점 그 둘의 대화 내용 아내에게 열등감을 느끼던 남편이 육아를 도맡고 아내의 바깥일을 내조하며 그녀를 진짜 히어로로 인정하는과정이 인상 깊었다  꿈이 많은 내게 선물같은 영화였다|   	10
4|	5|	불(catc****)|	 이런 게 최고의 애니매이션이 아니면 뭐란 말인가   미취학아동 때 1을 보고 대학생이 되어서 2를 보는 기분이란   ㅠㅠㅠ헬렌의 활약과 잭잭의 귀여움 그리고 개인적으로는 에드나의 매력까지 올해 본 영화 중 최고|     	10
5|	6|	쿠앤크(zhfl****)|	 잭잭 납치하러 갈 파티원 구합니다 1 10000 | 	8
6|	7|	아머두어라두(dkqj****)|	 관람객내 어릴적 베스트 영화의 속편이 너무 잘만들어져서 울컥했습니다|  	10
7|	8|	일산빵셔틀(rnra****)|	 평론가 임수연씨의 마블보다 재밌다는 평을보고 코웃음치고 보러갔는데 마블빠인 내가봐도 마블보다 재밌었다 히어로물은 언제까지나 마블의 독주일꺼라는 착각을 씻어내준 가족액션 히어로물 진짜 너무재밌다| 	10
8|	9|	물짱이(pres****)|	 관람객꼭 보세요 개 쩜  일라스티걸이 미스터인크레더블의 그늘에서 벗어나 활약하는 것도 멋지지만 미스터 인크레더블이 바뀐 역할을 얕보지 않고 가족을 위해서 열심히 노력하는 모습도 멋집니다  가족 구성원의 어떤 역할이든 중요|   	10
9|	10|	임태준(kota****)|	 일라스틱걸 사랑해요 ㅜㅜ| 	10
......

```
문장들의 가중치를 계산할 때, '~가, ~도'와 같은 불필요하게 가중치가 높아질 수 있는 단어들은 stopword라는 변수로 제외시킵니다.
아직 세탁소 댓글을 다 만들지 못해 stopword는 지정하지 않았습니다.
위 데이터에서 가중치가 높은 sents와 keyword를 뽑아 csv파일로 저장합니다.

```py
from konlpy.tag import Komoran
from summarizer import KeywordSummarizer
from summarizer import KeysentenceSummarizer
import pandas as pd
import string
import numpy as np
import sys

komoran = Komoran()		#Java로 이루어진 한국어 형태소 분석기

a = pd.read_csv('C:/py36/review_emotion.csv', encoding='utf-8')

'''py
a.columns=['num','ID','review','score']
a['review'] = a['review'].str.replace(pat='[^\w\s]', repl= ' ')  # replace all special symbols to space
a['review'] = a['review'].str.replace(pat='[\s\s+]', repl= ' ', regex=True)  # replace multiple spaces with a single space
'''

sentss = list(np.array(a['review'].tolist()))		#review열을 추출
sents = []

#sents에 리뷰 넣기
for i in range(1000):
    sents.insert(i,sentss[i])

#komoran 토크나이저(명사,동사,형용사 등으로 나눠줌)
def komoran_tokenize(sent):
    words = komoran.pos(sent, join=True)
    words = [w for w in words if ('/NN' in w or '/XR' in w or '/VA' in w or '/VV' in w)]
    return words

#keywordSummarizer를 통해 keyword 상위 10개 추출
keyword_extractor = KeywordSummarizer(
    tokenize = komoran_tokenize,
    window = -1,
    verbose = False
)
keywords = keyword_extractor.summarize(sents, topk=10)

summarizer = KeysentenceSummarizer(tokenize = komoran_tokenize, min_sim = 0.5)
keysents = summarizer.summarize(sents, topk=10)

keywords2 = pd.DataFrame(keywords)	#keyword를 데이터 프레임으로 변경
keywords2.columns = ["keywords","weights"]	#keyword df의 columns명 변경

keywords2.to_csv('C:/py36/review_emotion2.csv',encoding='utf-8-sig')
```
추출한 keyword입니다. 명사만 뽑을 수 있지만 혹시 몰라 형용사와 동사도 뽑았습니다. 차후에 세탁소 댓글이 완성되면 조정할 계획입니다.
```
--------|keywords|weights
--------|--------|----------
0|보/VV|36.62739196
1|관람객/NNG|30.84064907
2|잭/NNP|21.10252649
3|재밌/VA|17.58688637
4|편/NNB|14.03999885
5|영화/NNG|10.25931933
6|나오/VV|8.003162485
7|좋/VA|7.613481931
8|인크레더블/NNP|7.292621322
9|있/VV|7.158753249
```
  
  
뽑아낸 keyword는 세탁소를 대표하는 단어로 나타내어집니다.  

![가게 리스트](https://user-images.githubusercontent.com/17975141/96369881-0e256e00-1197-11eb-837f-13dc8c060b9b.jpg)
  
그리고 해쉬태그를 클릭하면 해쉬태그에 해당하는 모든 세탁소 목록이 나옵니다.  

![해쉬태그 클릭](https://user-images.githubusercontent.com/17975141/96369886-11205e80-1197-11eb-933a-cb2cfde82d45.png)

![이중 해쉬태그 검색](https://user-images.githubusercontent.com/17975141/96369888-12ea2200-1197-11eb-8c72-cf7afc97586b.png)



# refernce
https://excelsior-cjh.tistory.com/93  
https://lovit.github.io/nlp/2019/04/30/textrank/  
Mihalcea, R., & Tarau, P. (2004). Textrank: Bringing order into text. In Proceedings of the 2004 conference on empirical methods in natural language processing  
Erkan, G., & Radev, D. R. (2004). Lexrank: Graph-based lexical centrality as salience in text summarization. Journal of Artificial Intelligence Research, 22, 457-479  
Barrios, F., López, F., Argerich, L., & Wachenchauzer, R. (2016). Variations of the similarity function of textrank for automated summarization. arXiv preprint arXiv:1602.03606.  
