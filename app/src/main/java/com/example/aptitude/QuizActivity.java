package com.example.aptitude;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvQuestion, tvCount;
    private MaterialCardView card1, card2, card3, card4;
    private TextView tvOpt1, tvOpt2, tvOpt3, tvOpt4;
    private Button btnNext;
    private ProgressBar progressBar;

    private List<Question> questionList;
    private int currentIdx = 0;
    private int score = 0;
    private int selectedOption = 0; // Tracks which card is clicked (1-4)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // 1. Initialize Views (Connect Java to XML)
        tvQuestion = findViewById(R.id.tvQuestionText);
        tvCount = findViewById(R.id.tvQuestionCount);
        card1 = findViewById(R.id.optionCard1);
        card2 = findViewById(R.id.optionCard2);
        card3 = findViewById(R.id.optionCard3);
        card4 = findViewById(R.id.optionCard4);
        tvOpt1 = findViewById(R.id.tvOption1);
        tvOpt2 = findViewById(R.id.tvOption2);
        tvOpt3 = findViewById(R.id.tvOption3);
        tvOpt4 = findViewById(R.id.tvOption4);
        btnNext = findViewById(R.id.btnNext);
        progressBar = findViewById(R.id.quizProgress);

        // 2. LOAD DATA FIRST (This fills the questionList)
        loadQuestionsFromJSON();

        // 3. Setup UI only AFTER data is loaded
        setupCardClicks();

        // Now it's safe to check size because loadQuestionsFromJSON initialized it
        if (questionList != null && !questionList.isEmpty()) {
            progressBar.setMax(questionList.size()); // Set the bar limit
            displayQuestion();
        } else {
            Toast.makeText(this, "No questions found!", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 4. Button Listener
        btnNext.setOnClickListener(v -> {
            if (selectedOption == 0) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            checkAnswer();
            currentIdx++;

            if (currentIdx < questionList.size()) {
                displayQuestion();
            } else {
                Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
                intent.putExtra("FINAL_SCORE", score);
                intent.putExtra("TOTAL_QUESTIONS", questionList.size());
                intent.putExtra("QUESTION_LIST", (ArrayList<Question>) questionList);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setupCardClicks() {
        card1.setOnClickListener(v -> handleSelection(1));
        card2.setOnClickListener(v -> handleSelection(2));
        card3.setOnClickListener(v -> handleSelection(3));
        card4.setOnClickListener(v -> handleSelection(4));
    }

    private void handleSelection(int optionNum) {
        selectedOption = optionNum;
        resetCardStyles();

        // Highlight selected card
        MaterialCardView selected = getCardByNum(optionNum);
        if (selected != null) {
            selected.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#6200EE"))); // Modern Purple
            selected.setStrokeWidth(6);
            selected.setCardBackgroundColor(Color.parseColor("#F3E5F5")); // Light Purple tint
        }
    }

    private void resetCardStyles() {
        MaterialCardView[] cards = {card1, card2, card3, card4};
        for (MaterialCardView c : cards) {
            c.setStrokeWidth(0);
            c.setCardBackgroundColor(Color.WHITE);
        }
    }

    private MaterialCardView getCardByNum(int num) {
        if (num == 1) return card1;
        if (num == 2) return card2;
        if (num == 3) return card3;
        if (num == 4) return card4;
        return null;
    }

    private void displayQuestion() {
        Question q = questionList.get(currentIdx);
        tvQuestion.setText(q.getQuestionText());

        tvOpt1.setText(q.getOptA());
        tvOpt2.setText(q.getOptB());
        tvOpt3.setText(q.getOptC());
        tvOpt4.setText(q.getOptD());

        tvCount.setText("Question: " + (currentIdx + 1) + "/" + questionList.size());

        progressBar.setProgress(currentIdx + 1);

        selectedOption = 0;
        resetCardStyles();
    }

    private void checkAnswer() {
        questionList.get(currentIdx).setUserSelectedOption(selectedOption);
        if (selectedOption == questionList.get(currentIdx).getCorrectAnsNo()) {
            score++;
        }
    }

    // JSON Loading remains the same logic
    private void loadQuestionsFromJSON() {
        // 1. Initialize immediately so size() never calls on a null object
        questionList = new ArrayList<>();

        try {
            InputStream is = getAssets().open("questions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            String selectedTopic = getIntent().getStringExtra("TOPIC_NAME");

            if (selectedTopic == null) selectedTopic = "";

            ArrayList<Question> tempFilteredList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                // 2. IMPROVED LOGIC: If the topic contains "Mixed", take everything
                if (selectedTopic.toLowerCase().contains("mixed")) {
                    tempFilteredList.add(new Question(
                            obj.getString("question"),
                            obj.getString("optionA"),
                            obj.getString("optionB"),
                            obj.getString("optionC"),
                            obj.getString("optionD"),
                            obj.getInt("answer")
                    ));
                }
                // 3. Otherwise, look for the specific category
                else if (obj.getString("category").equalsIgnoreCase(selectedTopic)) {
                    tempFilteredList.add(new Question(
                            obj.getString("question"),
                            obj.getString("optionA"),
                            obj.getString("optionB"),
                            obj.getString("optionC"),
                            obj.getString("optionD"),
                            obj.getInt("answer")
                    ));
                }
            }

            Collections.shuffle(tempFilteredList);

            // Take 15 for Mixed, 10 for others
            int limit = selectedTopic.toLowerCase().contains("mixed") ? 15 : 10;
            int finalCount = Math.min(tempFilteredList.size(), limit);

            for (int i = 0; i < finalCount; i++) {
                questionList.add(tempFilteredList.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}