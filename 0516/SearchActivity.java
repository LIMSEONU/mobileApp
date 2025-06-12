package com.example.simplememo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText editSearch;
    private Button btnDoSearch;
    private ListView resultList;

    private MemoDBHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editSearch = findViewById(R.id.editSearch);
        btnDoSearch = findViewById(R.id.btnDoSearch);
        resultList = findViewById(R.id.resultList);

        dbHelper = new MemoDBHelper(this);
        searchResults = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        resultList.setAdapter(adapter);

        btnDoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMemos();
            }
        });

        // ✅ 뒤로가기 버튼 처리
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void searchMemos() {
        String keyword = editSearch.getText().toString().trim();
        if (keyword.isEmpty()) {
            Toast.makeText(this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT content FROM memos WHERE content LIKE ?", new String[]{"%" + keyword + "%"});

        searchResults.clear();
        while (cursor.moveToNext()) {
            searchResults.add(cursor.getString(0));
        }

        cursor.close();
        adapter.notifyDataSetChanged();

        if (searchResults.isEmpty()) {
            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
