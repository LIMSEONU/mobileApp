package com.example.todolistapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    private EditText titleInput;
    private Button dateBtn, timeBtn, saveBtn;
    private Calendar calendar;
    private long selectedTimeMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        titleInput = findViewById(R.id.titleInput);
        dateBtn = findViewById(R.id.dateBtn);
        timeBtn = findViewById(R.id.timeBtn);
        saveBtn = findViewById(R.id.saveBtn);

        calendar = Calendar.getInstance();

        dateBtn.setOnClickListener(v -> {
            new DatePickerDialog(this, (view, year, month, day) -> {
                calendar.set(year, month, day);
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        timeBtn.setOnClickListener(v -> {
            new TimePickerDialog(this, (view, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                selectedTimeMillis = calendar.getTimeInMillis();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
        });

        saveBtn.setOnClickListener(v -> {
            String title = titleInput.getText().toString().trim();
            if (!title.isEmpty() && selectedTimeMillis > System.currentTimeMillis()) {
                new TaskDBHelper(this).insertTask(title, selectedTimeMillis);
                scheduleAlarm(this, selectedTimeMillis, title);
                finish();
            }
        });
    }

    private void scheduleAlarm(Context context, long timeMillis, String title) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("title", title);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeMillis, pendingIntent);
    }
}
