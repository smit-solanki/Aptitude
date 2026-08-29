package com.smit.frenzyaptitude;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FormulaDescriptionActivity extends AppCompatActivity {

    private TextView tvTopicTitle, tvFormulaDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_description);

        tvTopicTitle = findViewById(R.id.tvTopicTitle);
        tvFormulaDetails = findViewById(R.id.tvFormulaDetails);

        Button youtubeBtn = findViewById(R.id.youtubeButton);
        youtubeBtn.setOnClickListener(v -> {
            String channelUrl = "https://www.youtube.com/@SSAptiHub";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(channelUrl));
            startActivity(intent);
        });

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
                formulaText.append("• Formula:\n (M1 * D1 * H1) / W1   =   (M2 * D2 * H2) / W2\n\n");
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
                formulaText.append("• Downstream Speed (u + v)  (Boat go same as Water flow)\n");
                formulaText.append("• Upstream Speed (u - v)  (Boat go opposite of Water flow)\n\n");
                formulaText.append("• Speed in Still Water = 1/2 (Down + Up)\n");
                formulaText.append("• Speed of Stream = 1/2 (Down - Up)");
                break;

            case "Pipes and Cisterns":
                formulaText.append("• Basic Concept:\n");
                formulaText.append("If a pipe fills a tank in 'x' hours, then part filled in 1 hour = 1/x\n\n");

                formulaText.append("• Inlet & Outlet:\n");
                formulaText.append("If Inlet fills in 'A' hours and Outlet empties in 'B' hours:\n");
                formulaText.append("Net work in 1 hour = (1/A) - (1/B)\n\n");

                formulaText.append("• Key Rule:\n");
                formulaText.append("If the result is positive, the tank is filling.\n");
                formulaText.append("If the result is negative, the tank is emptying.");
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