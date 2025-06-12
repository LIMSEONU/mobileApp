# 🎨 LayoutBackgroundEx

버튼 클릭 시 `ConstraintLayout`의 배경색이 무작위 색상(RGB)으로 변경되는 Android 앱입니다.  
단순하지만 직관적인 UI 인터랙션 예제로, 배경 색상 전환 효과를 연습할 수 있습니다.

## 🛠 개발 환경
- IDE: Android Studio
- 언어: Java
- 레이아웃: ConstraintLayout
- 패키지명: `com.example.layoutbackgroudex`

## ✅ 주요 기능
- 버튼 클릭 시 랜덤 색상으로 배경 변경
- `Color.rgb()`와 `Random` 클래스 사용
- 레이아웃 구조는 그대로 유지됨

## 📂 주요 파일
- `MainActivity.java`  
  - 버튼 클릭 시 랜덤 색상 생성 및 배경 적용  
  - 예시 코드:
    ```java
    int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    mainLayout.setBackgroundColor(color);
    ```
- `activity_main.xml`  
  - `ConstraintLayout` 기반 화면 구조  
  - 텍스트, 이미지, 버튼 포함

---

### 👨‍💻 개발자
- 임선우
- GitHub: [LIMSEONU](https://github.com/LIMSEONU)

