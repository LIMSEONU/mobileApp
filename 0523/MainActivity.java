package com.example.todolistapp;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TaskDBHelper dbHelper;
    private TaskAdapter adapter;
    private ArrayList<Task> taskList;
    private RecyclerView recyclerView;
    private Button addTaskBtn;
    private TextView currentTimeText;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }

        dbHelper = new TaskDBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        addTaskBtn = findViewById(R.id.btnAdd);
        currentTimeText = findViewById(R.id.currentTimeText);

        taskList = new ArrayList<>();
        adapter = new TaskAdapter(this, taskList, task -> {
            new AlertDialog.Builder(this)
                    .setTitle("삭제 확인")
                    .setMessage("이 항목을 삭제하시겠습니까?")
                    .setPositiveButton("삭제", (dialog, which) -> {
                        dbHelper.deleteTask(task.getId());
                        loadTasks();
                    })
                    .setNegativeButton("취소", null)
                    .show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        loadTasks();

        addTaskBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddTaskActivity.class);
            startActivity(intent);
        });

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    String currentTime = new SimpleDateFormat("a hh:mm:ss", Locale.getDefault()).format(new Date());
                    currentTimeText.setText("현재 시각: " + currentTime);
                });
            }
        }, 0, 1000);
    }

    private void loadTasks() {
        taskList.clear();
        Cursor cursor = dbHelper.getAllTasks();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            long time = cursor.getLong(2);
            taskList.add(new Task(id, title, time));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTasks();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
