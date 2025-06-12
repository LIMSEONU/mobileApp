package com.example.todolistapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class TaskDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "tasks.db";
    private static final int DB_VERSION = 1;

    public TaskDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertTask(String title, long time) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("time", time);
        db.insert("tasks", null, values);
    }

    public Cursor getAllTasks() {
        return getReadableDatabase().rawQuery("SELECT * FROM tasks ORDER BY time ASC", null);
    }

    public void deleteTask(int id) {
        getWritableDatabase().delete("tasks", "id=?", new String[]{String.valueOf(id)});
    }
}
