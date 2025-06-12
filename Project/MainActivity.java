package com.example.culturepick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerGenre;
    Button btnRecommend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerGenre = findViewById(R.id.spinnerGenre);
        btnRecommend = findViewById(R.id.btnRecommend);

        String[] genres = {"로맨스", "스포츠", "시대극", "범죄", "멜로", "액션"};
        spinnerGenre.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genres));

        //insertInitialContents();  // Firestore에 기본 영화 등록

        btnRecommend.setOnClickListener(v -> {
            String selectedGenre = spinnerGenre.getSelectedItem().toString();
            Intent intent = new Intent(MainActivity.this, RecommendActivity.class);
            intent.putExtra("genre", selectedGenre);
            startActivity(intent);
        });
    }
}

//    private void insertInitialContents() {
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        List<Map<String, Object>> contents = Arrays.asList(
//                // 로맨스
//                //Map.of("title", "사랑의 불시착", "genre", "로맨스", "description", "운명처럼 만난 남북 커플 이야기", "posterUrl", "https://upload.wikimedia.org/wikipedia/ko/c/cc/%EC%82%AC%EB%9E%91%EC%9D%98_%EB%B6%88%EC%8B%9C%EC%B0%A9_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg")
//        );
//
//
//        for (Map<String, Object> item : contents) {
//            db.collection("contents").add(item);
//        }
//    }
//}
