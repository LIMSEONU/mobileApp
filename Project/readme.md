# 🎬 CulturePick+

문화 콘텐츠(영화/드라마)를 장르별로 추천받고, 간단한 리뷰도 남길 수 있는 안드로이드 앱입니다.

## 📱 주요 기능

- 장르 선택 후 랜덤 추천
- 콘텐츠 설명 및 포스터 이미지 표시
- 리뷰 작성 (닉네임, 내용)
- 해당 콘텐츠의 모든 리뷰 조회
- Firebase Firestore를 통한 실시간 데이터 저장

## 🛠 사용 기술

- **언어**: Java
- **IDE**: Android Studio
- **DB**: Firebase Firestore
- **라이브러리**: Glide (이미지 로딩)

## 🔄 앱 흐름

1. 앱 실행 → 장르 선택 후 "추천 받기" 클릭
2. Firestore에서 해당 장르 중 랜덤 콘텐츠 추천
3. 포스터, 설명 표시
4. 리뷰 작성 및 저장
5. 리뷰 목록 확인 가능

## 🧪 폴더 구조
```
CulturePick/
├── java/
│ └── com.example.culturepick/
│ ├── MainActivity.java
│ ├── RecommendActivity.java
│ └── ReviewListActivity.java
├── res/
│ └── layout/
│ ├── activity_main.xml
│ ├── activity_recommend.xml
│ └── activity_review_list.xml
└── images/
├── screen_main.png
├── screen_recommend.png
└── screen_review_list.png
```
# 🔐 기타

- Firestore 컬렉션:
  - `contents`: 콘텐츠 정보 저장 (title, genre, description, posterUrl 등)
  - `reviews`: 리뷰 저장 (title, nickname, text, timestamp)

## 🙋 개발자

> CulturePick+  
> 개발: 임선우
> 🔗 Powered by Android + Firebase
