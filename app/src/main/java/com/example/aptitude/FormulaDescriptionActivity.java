package com.example.aptitude;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FormulaDescriptionActivity extends AppCompatActivity {

    private TextView tvTopicTitle, tvFormulaDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_description);

        // Get the Topic Name passed from the previous activity
        String selectedTopic = getIntent().getStringExtra("TOPIC_NAME");

        if (selectedTopic != null) {
            tvTopicTitle.setText(selectedTopic);
            displayFormulaForTopic(selectedTopic);
        }
    }

    private void displayFormulaForTopic(String topic) {
        StringBuilder formulaText = new StringBuilder();

        // 4. Logical Filter for each topic
        switch (topic) {
            case "Chain Rule":
                formulaText.append("• Formula: M1 * D1 * H1 / W1 \n=\n M2 * D2 * H2 / W2\n\n");
                formulaText.append("• More Work, More Men (Direct)\n");
                formulaText.append("• More Men, Less Days (Indirect)");
                break;

            case "Time and Work":
                formulaText.append("• Work Done = Time * Rate\n\n");
                formulaText.append("• If A can do work in x days, 1 day work = 1/x\n");
                formulaText.append("• If A is twice as good as B, Ratio of work = 2:1");
                break;

            case "Problems on Trains":
                formulaText.append("• Speed = Distance / Time\n\n");
                formulaText.append("• km/hr to m/s = Multiply by 5/18\n");
                formulaText.append("• Relative Speed (Same Dir) = S1 - S2\n");
                formulaText.append("• Relative Speed (Opposite Dir) = S1 + S2");
                break;

            case "Boats and Streams":
                formulaText.append("• Downstream Speed (u + v)\n");
                formulaText.append("• Upstream Speed (u - v)\n\n");
                formulaText.append("• Speed in Still Water = 1/2 (Down + Up)\n");
                formulaText.append("• Speed of Stream = 1/2 (Down - Up)");
                break;

            default:
                formulaText.append("Formula details for " + topic + " will be updated soon on SSAptiHub.");
                break;
        }

        tvFormulaDetails.setText(formulaText.toString());
    }

    // Handles the back button in the toolbar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}