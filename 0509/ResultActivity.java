package com.example.surveyapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView resultText = findViewById(R.id.resultText);
        BarChart chart = findViewById(R.id.barChart);

        int total = 0;
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < SurveyManager.answers.length; i++) {
            int score = SurveyManager.answers[i];
            total += score;
            entries.add(new BarEntry(i, score));
        }
        float avg = total / (float) SurveyManager.answers.length;

        String message;
        if (avg <= 2.0) message = "디지털 디톡스 상태가 훌륭해요!";
        else if (avg <= 3.5) message = "일정 부분 디지털 노출이 있었어요.";
        else message = "디지털 피로가 높습니다. 휴식이 필요해요.";

        resultText.setText("평균 점수: " + avg + "\n" + message);

        BarDataSet dataSet = new BarDataSet(entries, "디지털 피로 점수");
        dataSet.setColor(Color.parseColor("#FF9800"));
        BarData data = new BarData(dataSet);
        chart.setData(data);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.invalidate();
    }
}
