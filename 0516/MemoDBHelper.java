package com.example.simplememo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MemoDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "memo.db";
    public static final int DB_VERSION = 1;

    public MemoDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE memos (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 버전 업그레이드시 테이블 재생성
        db.execSQL("DROP TABLE IF EXISTS memos;");
        onCreate(db);
    }
    public void deleteMemo(String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("memos", "content = ?", new String[]{content});
    }
}
