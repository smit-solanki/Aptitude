package com.smit.frenzyaptitude;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.card.MaterialCardView;

import java.util.Calendar;

public class DashboardActivity extends AppCompatActivity {
    MaterialCardView btn_formula , btn_quiz , btn_answer;
    private AdView mAdView;
    private static final String STREAK_PREFS_NAME = "streak_prefs";
    private static final String KEY_STREAK = "current_streak";
    private static final String KEY_LAST_OPEN_EPOCH_DAY = "last_open_epoch_day";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // 1. Initialize AdMob SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // 2. Load the Banner Ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // 3. Update and display the current streak
        TextView streakCountText = findViewById(R.id.streakCountText);
        int streak = updateAndGetStreak();
        streakCountText.setText(String.valueOf(streak));

        btn_formula = findViewById(R.id.btnFormulaOption);
        btn_formula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, FormulaActivity.class);
                startActivity(i);
            }
        });
        btn_quiz = findViewById(R.id.btnQuizOption);
        btn_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btn_answer = findViewById(R.id.btnMCQSolutionOption);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AnswerActivity.class);
                startActivity(i);
            }
        });

    }

    // Compares today's date to the last saved open date:
    // same day -> unchanged, exactly 1 day later -> +1, otherwise -> resets to 1
    private int updateAndGetStreak() {
        SharedPreferences prefs = getSharedPreferences(STREAK_PREFS_NAME, MODE_PRIVATE);

        long today = todayEpochDay();
        long lastOpen = prefs.getLong(KEY_LAST_OPEN_EPOCH_DAY, -1);
        int streak = prefs.getInt(KEY_STREAK, 0);

        if (lastOpen == today) {
            // already counted today
        } else if (lastOpen == today - 1) {
            streak += 1;
        } else {
            streak = 1;
        }

        prefs.edit()
                .putInt(KEY_STREAK, streak)
                .putLong(KEY_LAST_OPEN_EPOCH_DAY, today)
                .apply();

        return streak;
    }

    private long todayEpochDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() / (24L * 60 * 60 * 1000);
    }
}