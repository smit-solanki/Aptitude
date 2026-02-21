package com.example.aptitude;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class FormulaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdView mAdView;
    private List<Topic> topicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);

        // 1. Initialize AdMob SDK
        MobileAds.initialize(this, initializationStatus -> {});

        // 2. Load the Banner Ad
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        recyclerView = findViewById(R.id.recyclerViewTopics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button youtubeBtn = findViewById(R.id.youtubeButton);
        youtubeBtn.setOnClickListener(v -> {
            String channelUrl = "https://www.youtube.com/@SSAptiHub";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(channelUrl));
            startActivity(intent);
        });

        // Your Topic List
        topicList = new ArrayList<>();
        topicList.add(new Topic("Chain Rule"));
        topicList.add(new Topic("Time and Work"));
        topicList.add(new Topic("Boats and Streams"));
        topicList.add(new Topic("Problems on Trains"));
        topicList.add(new Topic("Pipes and Cisterns"));

        TopicAdapter adapter = new TopicAdapter(topicList, topic -> {
            Intent intent = new Intent(FormulaActivity.this, FormulaDescriptionActivity.class);
            intent.putExtra("TOPIC_NAME", topic.getName());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

    }
}