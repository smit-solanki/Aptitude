package com.example.aptitude;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.progressindicator.CircularProgressIndicator; // Import this

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private ArrayList<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
}