package com.example.simplememo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ViewAllActivity extends AppCompatActivity {

    private ListView listAll;
    private MemoDBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> allMemos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        listAll = findViewById(R.id.listAll);
        dbHelper = new MemoDBHelper(this);
        allMemos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allMemos);
        listAll.setAdapter(adapter);

        loadAllMemos();

        // ✅ 뒤로가기 버튼
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(ViewAllActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // ✅ 삭제 기능 (길게 누르면)
        listAll.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedMemo = allMemos.get(position);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("삭제 확인")
                    .setMessage("이 메모를 삭제하시겠습니까?\n\n" + selectedMemo)
                    .setPositiveButton("예", (dialog, which) -> {
                        dbHelper.deleteMemo(selectedMemo);   // DB에서 삭제
                        loadAllMemos();                      // 목록 갱신
                        Toast.makeText(this, "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("아니요", null)
                    .show();

            return true;
        });
    }



    // 전체 메모를 DB에서 불러오는 함수
    private void loadAllMemos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // DB 연결
        Cursor cursor = db.rawQuery("SELECT content FROM memos", null); // 전체 메모 불러오기

        allMemos.clear(); // 기존 리스트 비우기
        while (cursor.moveToNext()) {
            allMemos.add(cursor.getString(0)); // 메모 내용 리스트에 추가
        }

        cursor.close(); // 자원 정리
        adapter.notifyDataSetChanged(); // ListView 화면 갱신
    }
}
