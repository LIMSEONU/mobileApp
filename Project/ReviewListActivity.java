package com.example.culturepick;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;

public class ReviewListActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView textTitle;
    ListView listViewReviews;
    ArrayAdapter<String> adapter;
    ArrayList<String> reviewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);


        textTitle = findViewById(R.id.textReviewTitle);
        listViewReviews = findViewById(R.id.listViewReviews);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reviewList);
        listViewReviews.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        String title = getIntent().getStringExtra("title");
        textTitle.setText("📌 " + title + " 리뷰 목록");

        db.collection("reviews")
                .whereEqualTo("title", title)
                .orderBy("timestamp")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    reviewList.clear(); // 초기화
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        String nickname = doc.getString("nickname");
                        String text = doc.getString("text");
                        Long timestamp = doc.getLong("timestamp");

                        String time = "";
                        if (timestamp != null) {
                            Date date = new Date(timestamp);
                            time = DateFormat.format("MM/dd HH:mm", date).toString();
                        }

                        String formatted = "👤 " + nickname + "\n💬 " + text + "\n🕒 " + time;
                        reviewList.add(formatted);
                    }
                    adapter.notifyDataSetChanged();
                });
        Button btnGoHome = findViewById(R.id.btnGoHome);
        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(ReviewListActivity.this, MainActivity.class);
            // 이전 액티비티 스택 제거 (선택)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish(); // 현재 화면 종료
        });
    }

}
