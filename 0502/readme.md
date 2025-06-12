# 🖼️ 전시 콘텐츠 슬라이드 앱

`ViewPager2`를 활용해 영화, 뮤지컬, 연극 등 다양한 문화 예술 작품을 슬라이드 형식으로 보여주는 Android 앱입니다.  
각 페이지에는 작품 이미지와 제목이 함께 표시되며, 사용자는 좌우로 넘기며 콘텐츠를 탐색할 수 있습니다.

## 🛠 개발 환경
- IDE: Android Studio
- 언어: Java / XML
- 패키지명: com.example.recyclerview

## ✅ 주요 기능
- `ViewPager2`를 통한 슬라이드형 콘텐츠 탐색
- 각 콘텐츠는 이미지와 제목으로 구성
- 사용자 친화적인 카드형 전시 느낌 제공

## 📂 주요 구성
- `activity_main.xml`: `ViewPager2`로 전체 화면 구성
- `item_artwork.xml`: 한 작품의 이미지 + 제목 표시 뷰
- `Artwork.java`: 작품 데이터를 담는 모델 클래스
- `ArtworkAdapter.java`: RecyclerView 어댑터로 ViewPager 연결
- `MainActivity.java`: 데이터 리스트 생성 및 ViewPager 설정

## 📌 등록된 예시 작품
- 2025 영화 베스트 작품 (야당)
- 2024-25 뮤지컬 베스트 작품 (시지프스)
- 2024 연극 베스트 작품 (맥베스)

---

### 👨‍💻 개발자
- 임선우  
- GitHub: [LIMSEONU](https://github.com/LIMSEONU)

