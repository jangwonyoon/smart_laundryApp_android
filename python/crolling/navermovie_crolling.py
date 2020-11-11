import requests
from bs4 import BeautifulSoup
#from datetime import datetime
import sys

sys.stdout = open('C:/py36/example.csv','w',newline="")

def get_data(url):
    resp = requests.get(url)
    html = BeautifulSoup(resp.content, 'html.parser')
    score_result = html.find('div', {'class': 'score_result'})
    lis = score_result.findAll('li')

    for li in lis:
        befornickname = li.find('dl')
        nickname = befornickname.findAll('a')[0].find('span').getText()
        #created_at = datetime.strptime(li.find('dt').findAll('em')[-1].getText(), "%Y.%m.%d %H:%M")

        review_text = li.find('p').getText()
        score = li.find('em').getText()
        #btn_likes = li.find('div', {'class': 'btn_area'}).findAll('span')
        #like = btn_likes[1].getText()
        #dislike = btn_likes[3].getText()

        #watch_movie = li.find('span', {'class':'ico_viewer'})

        # 간단하게 프린트만 했습니다.
        #print(nickname, review_text, score, like, dislike, created_at, watch_movie and True or False)
        print(nickname, review_text, score)

#url 연결하기
test_url = 'https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code=136990&type=after'
resp = requests.get(test_url)
html = BeautifulSoup(resp.content, 'html.parser')
result = html.find('div', {'class':'score_total'}).find('strong').findChildren('em')[0].getText()
total_count = int(result.replace(',', ''))

for i in range(1, int(total_count / 10) + 1):
    url = test_url + '&page=' + str(i)
    print('url: "' + url + '" is parsing....')
    get_data(url)


