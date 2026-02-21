package com.example.aptitude;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FormulaActivity extends AppCompatActivity {

    private TextView tvTitle, tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);

        // Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarLearning);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Study Concept");

        tvTitle = findViewById(R.id.tvTopicTitle);
        tvContent = findViewById(R.id.tvFormulaContent);

        // Get the topic name passed from MainActivity
        String topic = getIntent().getStringExtra("TOPIC_NAME");
        if (topic == null) topic = "General Aptitude";

        tvTitle.setText(topic);
        loadFormula(topic);
    }

    private void loadFormula(String topic) {
        String data = "";

        if (topic.equals("Chain Rule")) {
            data = "• M1 * D1 * H1 / W1 = M2 * D2 * H2 / W2\n\n" +
                    "• Direct Proportion: More Articles = More Cost\n" +
                    "• Indirect Proportion: More Men = Less Days";
        }
        else if (topic.equals("Time and Work")) {
            data = "• Work Done = Time * Rate\n\n" +
                    "• If A can do work in n days, 1 day work = 1/n\n" +
                    "• Total Work = LCM of individual days";
        }
        else if (topic.equals("Problems on Trains")) {
            data = "• Speed = Distance / Time\n\n" +
                    "• km/hr to m/s: Multiply by 5/18\n" +
                    "• m/s to km/hr: Multiply by 18/5\n" +
                    "• Relative Speed (Opposite): S1 + S2";
        }
        else {
            data = "Visit SSAptiHub for more details on " + topic;
        }

        tvContent.setText(data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}