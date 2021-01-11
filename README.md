## 홍익대학교 스마트 세탁 어플리케이션

---

## 1. Intro

- **팀 명 :**  Keep_going
- **프로젝트 명 :** 세탁만세
- **프로젝트 형태 :**  홍익대학교 캡스톤 디자인
- **팀원 :** [윤장원(팀장)](https://github.com/jangwonyoon), [이의연](https://github.com/euiyeonlee), [전종욱](https://github.com/EDIT0), [여태권](https://github.com/taekyeo110)
- **사이트 배포 링크( AWS S3 ) :** [http://laudaryservice.s3-website.ap-northeast-2.amazonaws.com/](http://laudaryservice.s3-website.ap-northeast-2.amazonaws.com/)
- **세탁이** :[http://edit0.dothome.co.kr/makeweb/web_searching.php](http://edit0.dothome.co.kr/makeweb/web_searching.php)
- [http://edit0.dothome.co.kr/makeweb/web_o_login.php](http://edit0.dothome.co.kr/makeweb/web_o_login.php)
- **안드로이드 배포 :**
- **EC2 ( AWS EC2)**
    - 퍼블릭 DNS(IPv4) : [ec2-3-34-125-187.ap-northeast-2.compute.amazonaws.com](http://ec2-3-34-125-187.ap-northeast-2.compute.amazonaws.com/)
    - 퍼블릭 IP : 3.34.125.187
    - 퍼블릭 Phpmyadmin : [http://3.34.125.187/edit0/](http://3.34.125.187/edit0/)

## 2. Project

# 프로젝트 소개

**세탁물을 원하는 시간에 받고 싶은 경우, 세탁물 맡기기 귀찮거나 받으러 가기 귀찮은 경우 세탁물 대행 서비스입니다.** 

## 3.Team member

### 팀장 : 윤장원

- **Role** : Team Leader
- **Position** : Full-stack, dev-ops
- **Stack** : React, React-Hooks, material-ui, style-components, AWS S3, AWS EC2 , AWS RDS , mysql, html, css, javaScript , android-material-design
- **Works**
    - **PM**
        - 개발 스케줄링 및 팀원과의 커뮤케이션 담당
        - 팀원간의 역할 업무 분배 담당
        - QA 진행 ( 안드로이드 기능 , 웹페이지 컴포넌트, css )
    - **프론트 엔드 개발**
        - UI/UX 설계
        - React SPA를 통한 세탁 페이지 구현
        - material-ui 기반으로 한 레이아웃 구성
        - 웹팩을 이용한 build
        - 안드로이드 UI/UX 디자인 설계 및 구현
    - **백엔드 개발**
        - 데이터베이스 관계스키마 설계
        - 안드로이드 스튜디오 앱 Design 개발
    - **배포**
        - AWS S3를 통한 웹페이지 배포
        - AWS EC2를 통한 서버 구축 ( ubuntu 16.04 .version)
            - LAMP ( Linux , apache2 , mysql, Phpmysql ) 연동 및 배포
    - **협업**
        - Slack을 통한 빠른 소통 구축
        - GitHub를 통한 코드 리뷰 및 개발

    ### 팀원 : 이의연
    
    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d42cf934-7491-4ee1-bf90-131a1a602a1c/.jpeg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/d42cf934-7491-4ee1-bf90-131a1a602a1c/.jpeg)

    - **Role** : Team Member
    - **Position** : Data analyst ,Back-end
    - **Stack** : Mysql, Python, R, Tensorflow
    - **Works**
        - **Back-end**
            - 데이터베이스 스키마 구축
        - **Front-end**
            - UI/UX 설계
        - **Data Analysis**
            - Web Crolling 및 데이터 전처리 (Python)
            - 데이터 모델링 및 학습 (Tensorflow)
            - 데이터 분석 및 시각화 (R)
        - **협업**
            - Slack을 통한 빠른 소통
            - Git을 통한 코드 리뷰 및 개발

    ### 팀원 : 전종욱

    ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f1cab579-66ec-4731-bff6-e6ef338b1a13/.jpeg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f1cab579-66ec-4731-bff6-e6ef338b1a13/.jpeg)

    - **Role** : Full-stack , Native developer(android)
    - **Position** : Team Member
    - **Stack** : Android , php , mysql , phpMyadmin
    - **Works**
        - **Native 개발**
            - 안드로이드 앱 기능 구현
            - Client, Owner, Deliver app 개발
            - Android <-> PHP <-> Mysql
        - **Back-End**
            - 데이터 베이스 관계 스키마 구축 및 구현
            - 데이터 베이스 mysql, phpMyadmin 연동 및 쿼리 작성
            - FTP 파일 전송 프로토콜을 통한 파일 전송
        - **배포**
            - 안드로이드 App을 apk파일로 배포
        - **협업**
            - Slack을 통한 빠른 소통
            - GitHub를 통한 코드 리뷰 및 개발

        ### 팀원 : 여태권

        ![https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a72399ba-dec4-4a7e-a3bf-dabf5838339f/.jpeg](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/a72399ba-dec4-4a7e-a3bf-dabf5838339f/.jpeg)

        - **Role** : Team Member
        - **Position** : Back-end, Data analyst
        - **Stack** : python, tensorflow , mysql, R
        - **Works**
            - **Back - end**
                - 데이터베이스 스키마 구축
                - 데이터베이스 관계 정리 및 정규화 작업
                - 데이터베이스 구현(mysql)
            - **Data Analysis**
                - Web Crolling 및 데이터 전처리 (Python)
                - 데이터 모델링 및 학습 (Tensorflow)
                - 데이터 분석 및 시각화 (R)
            - **협업**
                - GitHub을 통한 코드 리뷰 및 개발
                - Slack을 통한 빠른 소통
                
##4. 데모영상
<iframe width="896" height="504" src="https://www.youtube.com/embed/PxrmLCCEaHU" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
