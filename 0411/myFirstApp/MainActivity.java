package com.example.dongeulapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button btnTime, btnTasks, btnCafe, btnNote, btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnTime = findViewById(R.id.btnTime);
        btnTasks = findViewById(R.id.btnTasks);
        btnCafe = findViewById(R.id.btnCafe);
        btnNote = findViewById(R.id.btnNote);
        btnSetting = findViewById(R.id.btnSettings);

        btnTime.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimeActivity.class);
                startActivity(intent);
            }
        });

        btnTasks.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

        btnCafe.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CafeActivity.class);
                startActivity(intent);
            }
        });

        btnNote.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }
}