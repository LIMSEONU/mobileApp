package com.example.culturepick;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RecommendActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView textTitle, textDescription;
    ImageView imagePoster;
    EditText editReview, editNickname;
    Button btnSubmitReview, btnViewReviews;

    String contentId = "";
    String contentTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        // ✅ UI 요소 연결은 반드시 setContentView 다음에!
        textTitle = findViewById(R.id.textTitle);
        textDescription = findViewById(R.id.textDescription);
        imagePoster = findViewById(R.id.imagePoster);
        editReview = findViewById(R.id.editReview);
        editNickname = findViewById(R.id.editNickname); // ← 문제 해결된 부분
        btnSubmitReview = findViewById(R.id.btnSubmitReview);
        btnViewReviews = findViewById(R.id.btnViewReviews);

        db = FirebaseFirestore.getInstance();

        // 전달받은 장르 가져오기
        String genre = getIntent().getStringExtra("genre");

        // Firestore에서 해당 장르의 콘텐츠 중 랜덤 1개 가져오기
        db.collection("contents")
                .whereEqualTo("genre", genre)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<DocumentSnapshot> docs = querySnapshot.getDocuments();

                    if (docs.isEmpty()) {
                        textTitle.setText("추천할 콘텐츠가 없습니다 😢");
                        return;
                    }

                    DocumentSnapshot doc = docs.get(new Random().nextInt(docs.size()));
                    contentId = doc.getId();
                    contentTitle = doc.getString("title");

                    String description = doc.getString("description");
                    String posterUrl = doc.getString("posterUrl");

                    textTitle.setText(contentTitle);
                    textDescription.setText(description);

                    if (posterUrl != null && !posterUrl.isEmpty()) {
                        Glide.with(this).load(posterUrl).into(imagePoster);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "데이터 불러오기 실패", Toast.LENGTH_SHORT).show();
                });

        // 리뷰 저장
        btnSubmitReview.setOnClickListener(v -> {
            String reviewText = editReview.getText().toString().trim();
            String nickname = editNickname.getText().toString().trim();

            if (reviewText.isEmpty()) {
                Toast.makeText(this, "리뷰를 입력해주세요", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> review = Map.of(
                    "contentId", contentId,
                    "title", contentTitle,
                    "nickname", nickname,
                    "text", reviewText,
                    "timestamp", System.currentTimeMillis()
            );

            db.collection("reviews")
                    .add(review)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(this, "리뷰 저장 완료!", Toast.LENGTH_SHORT).show();
                        editReview.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "리뷰 저장 실패", Toast.LENGTH_SHORT).show();
                    });
        });

        // 리뷰 목록 보기
        btnViewReviews.setOnClickListener(v -> {
            Intent intent = new Intent(RecommendActivity.this, ReviewListActivity.class);
            intent.putExtra("title", contentTitle);
            startActivity(intent);
        });
    }
}
