package com.example.aptitude;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.progressindicator.CircularProgressIndicator; // Import this

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;
    private ArrayList<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        MobileAds.initialize(this, initializationStatus -> {});
        loadInterstitialAd();
        // 1. Get Data from Intent
        int score = getIntent().getIntExtra("FINAL_SCORE", 0);
        int total = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);
        questionList = (ArrayList<Question>) getIntent().getSerializableExtra("QUESTION_LIST");

        // 2. Initialize Views
        TextView tvScoreText = findViewById(R.id.tvFinalScore);
        CircularProgressIndicator scoreProgress = findViewById(R.id.scoreProgress);
        RecyclerView rvReview = findViewById(R.id.rvReview);
        Button btnHome = findViewById(R.id.btnHome);

        // 3. Update Text (e.g., "8/10")
        tvScoreText.setText(score + "/" + total);

        // 4. Update Circular Progress (Calculate Percentage)
        if (total > 0) {
            // (Score / Total) * 100 gives the percentage for the circle
            int percentage = (int) (((float) score / total) * 100);
            scoreProgress.setProgress(percentage);

            // Optional: Use this for a smooth animation
            // scoreProgress.setProgressCompat(percentage, true);
        }

        // 5. Setup Review RecyclerView
        rvReview.setLayoutManager(new LinearLayoutManager(this));
        ResultAdapter adapter = new ResultAdapter(questionList);
        rvReview.setAdapter(adapter);

        // 6. Home Button
        btnHome.setOnClickListener(v -> finish());
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-6920326538569130/6887545073", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        // 3. SHOW THE AD immediately once it loads
                        mInterstitialAd.show(ResultActivity.this);
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }
}