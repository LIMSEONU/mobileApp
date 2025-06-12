package com.example.surveyapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class QuestionActivity extends AppCompatActivity {
    private static final String[] questions = {
            "오늘 스마트폰 사용 시간은 많았나요?",
            "SNS를 자주 확인했나요?",
            "알림을 자주 받았거나 확인했나요?",
            "아침에 일어나자마자 스마트폰을 봤나요?",
            "자기 전에 폰을 오래 사용했나요?",
            "하루 종일 전자기기에 의존했다고 느끼나요?",
            "노트북/태블릿을 많이 사용했나요?",
            "전자기기 없이 보낸 시간이 있었나요?",
            "종이책을 읽거나 아날로그 활동을 했나요?",
            "디지털 피로를 느꼈나요?"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        int index = getIntent().getIntExtra("questionIndex", 0);
        TextView questionText = findViewById(R.id.questionText);
        questionText.setText(questions[index]);

        RadioGroup options = findViewById(R.id.optionsGroup);
        Button nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(v -> {
            int selectedId = options.getCheckedRadioButtonId();
            if (selectedId != -1) {
                RadioButton selected = findViewById(selectedId);
                int score = Integer.parseInt(selected.getText().toString());
                SurveyManager.answers[index] = score;

                if (index < questions.length - 1) {
                    Intent intent = new Intent(this, QuestionActivity.class);
                    intent.putExtra("questionIndex", index + 1);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(this, ResultActivity.class));
                }
                finish();
            }
        });
    }
}
