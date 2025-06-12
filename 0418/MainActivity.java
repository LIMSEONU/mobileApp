package com.example.eggtimer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "egg_timer_channel";
    private TextView statusText;
    private EditText inputSeconds;
    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        inputSeconds = findViewById(R.id.inputSeconds);
        Button startButton = findViewById(R.id.startButton);

        createNotificationChannel();

        startButton.setOnClickListener(v -> {
            String input = inputSeconds.getText().toString();

            if (!input.isEmpty()) {
                int seconds = Integer.parseInt(input);
                long delayMillis = seconds * 1000L;

                statusText.setText(seconds + "초 삶는 중...");
                startTimer(delayMillis);
            } else {
                statusText.setText("시간을 입력해주세요!");
            }
        });
    }

    private void startTimer(long totalMillis) {
        if (timerRunnable != null) {
            handler.removeCallbacks(timerRunnable);
        }

        final int[] secondsLeft = {(int) (totalMillis / 1000)};
        statusText.setText(secondsLeft[0] + "초 삶는 중...");

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                secondsLeft[0]--;

                if (secondsLeft[0] > 0) {
                    statusText.setText(secondsLeft[0] + "초 삶는 중...");
                    handler.postDelayed(this, 1000);
                } else {
                    statusText.setText("완료!");
                    showNotification();
                    showExtendDialog();
                }
            }
        };

        handler.postDelayed(timerRunnable, 1000);
    }

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Egg Timer")
                .setContentText("계란 삶기가 완료되었습니다.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    private void showExtendDialog() {
        new AlertDialog.Builder(this)
                .setTitle("시간 연장")
                .setMessage("10초 더 삶을까요?")
                .setPositiveButton("예", (dialog, which) -> {
                    statusText.setText("10초 연장 중...");
                    startTimer(10000); // 10초 연장
                })
                .setNegativeButton("아니오", (dialog, which) -> {
                    statusText.setText("삶기 완료!");
                })
                .show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Egg Timer Channel";
            String description = "Timer channel for eggs";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
