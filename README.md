## 홍익대학교 스마트 세탁 어플리케이션

---

## 1. Intro

- **팀 명 :** Keep_going
- **프로젝트 명 :** 세탁만세
- **프로젝트 형태 :** 홍익대학교 캡스톤 디자인
- **팀원 :** [윤장원(팀장)](https://github.com/jangwonyoon), [이의연](https://github.com/euiyeonlee), [전종욱](https://github.com/EDIT0), [여태권](https://github.com/taekyeo110)
- **사이트 배포 링크( AWS S3 ) :** [http://laudaryservice.s3-website.ap-northeast-2.amazonaws.com/](http://laudaryservice.s3-website.ap-northeast-2.amazonaws.com/)
- **세탁이** :[http://edit0.dothome.co.kr/makeweb/web_searching.php](http://edit0.dothome.co.kr/makeweb/web_searching.php)
- [http://edit0.dothome.co.kr/makeweb/web_o_login.php](http://edit0.dothome.co.kr/makeweb/web_o_login.php)

- **EC2 ( AWS EC2)**
  - 퍼블릭 DNS(IPv4) : [ec2-3-34-125-187.ap-northeast-2.compute.amazonaws.com](http://ec2-3-34-125-187.ap-northeast-2.compute.amazonaws.com/)
  - 퍼블릭 IP : 3.34.125.187
  - 퍼블릭 Phpmyadmin : [http://3.34.125.187/edit0/](http://3.34.125.187/edit0/)

## 2. Project

# 프로젝트 소개

**세탁물을 원하는 시간에 받고 싶은 경우, 세탁물 맡기기 귀찮거나 받으러 가기 귀찮은 경우 세탁물 대행 서비스입니다.**

## 3.Team member

### 팀장 : 윤장원

<p align="center">
  <img src="https://user-images.githubusercontent.com/33803975/107325449-1b22f000-6aed-11eb-8e6a-08829eb5401d.png" width="20%" />
</p>

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

<p align="center">
  <img src="https://user-images.githubusercontent.com/33803975/107325439-1827ff80-6aed-11eb-8631-b3e91280a234.jpeg" width="20%" />
</p>

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

  <p align="center">
    <img src="https://user-images.githubusercontent.com/33803975/107325438-178f6900-6aed-11eb-932d-2cfdad0f4e41.jpeg" width="20%" />
  </p>

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

<p align="center">
  <img src="https://user-images.githubusercontent.com/33803975/107325430-12cab500-6aed-11eb-9097-a2a4ee4e5154.jpeg" width="20%" />
</p>

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

## 4. 데모영상

[![세탁이Demo](https://media.vlpt.us/images/jangwonyoon/post/25e2e52a-18c7-4863-9af8-99e56aa0401b/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA%202020-11-12%20%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE%201.34.26.png)](https://www.youtube.com/watch?v=PxrmLCCEaHU&t=46s)

- QR코드

![1C0DD869-82F2-4DDF-BBCE-1CB29303FD17_4_5005_c](https://user-images.githubusercontent.com/33803975/107326269-79040780-6aee-11eb-8d02-dc5e6ecccf7a.jpeg)

## 5. 성과

- 홍익대학교 캡스톤디자인 학술 경진대회 최우수상 수상 20.11.18
- 한국정보과학회(Kiise) KSC 주니어/학부생 논문기재 논문번호 106번(TextRank keyword 분석을 통한 세탁어플리케이션: 세탁이) 20.12.18
- 한국정보과학회(kilise) 한국정보과학회(Kiise) KSC2020 한국 소프트웨어종합학술대회 장려상 21.02

### **홍익대학교 캡스톤디자인 학술 경진대회**

<table>
  <tr>
    <td valign="top"><img src="https://user-images.githubusercontent.com/33803975/107326751-4f97ab80-6aef-11eb-9324-e75c9ee6c52c.jpeg"/></td>
    <td valign="top">
    <img src="https://user-images.githubusercontent.com/33803975/107326804-663e0280-6aef-11eb-88b1-23c54b0fbc96.jpeg"/>
    </td>
  </tr>
</table>

### KSC 2020 한국 소프트웨어종합학술대회

<p align="center">
<img src="https://user-images.githubusercontent.com/33803975/107327428-773b4380-6af0-11eb-9f30-fadd7874a3e7.png" width="50%" height="20%">
</p>
