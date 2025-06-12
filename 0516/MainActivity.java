package com.example.simplememo;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editTodo;
    private Button btnSave, btnSearch, btnViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main.xml이 layout 파일 이름

        editTodo = findViewById(R.id.editTodo);
        btnSave = findViewById(R.id.btnSave);
        btnSearch = findViewById(R.id.btnSearch);
        btnViewAll = findViewById(R.id.btnViewAll);

        // 저장 버튼 클릭 시
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });

        // 찾기 버튼 클릭 시 (SQLiteSearchActivity로 이동)
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // 전체 보기 버튼 클릭 시 (모든 메모 목록 보기)
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
                startActivity(intent);
            }
        });
    }

    // 텍스트 파일로 저장
    private void saveToFile() {
        String text = editTodo.getText().toString().trim();

        if (text.isEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // ✅ 날짜/시간 포맷 추가
        String timestamp = new java.text.SimpleDateFormat("yyyy년 M월 d일 HH:mm", java.util.Locale.getDefault()).format(new java.util.Date());
        String fullText = timestamp + " - " + text;

        try {
            // ✅ 1. 파일 저장
            FileOutputStream fos = openFileOutput("memo.txt", MODE_APPEND);
            fos.write((fullText + "\n").getBytes());
            fos.close();

            // ✅ 2. DB 저장
            MemoDBHelper dbHelper = new MemoDBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO memos (content) VALUES (?);", new Object[]{fullText});

            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            editTodo.setText("");

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT).show();
        }
    }


}
