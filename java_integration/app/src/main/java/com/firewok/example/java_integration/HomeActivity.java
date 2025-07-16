package com.firewok.example.java_integration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.feedintegration.MainActivity;
import com.firewok.example.java_integration.live.LiveMainActivity;
import com.firewok.example.java_integration.feedresource.FeedResourceMainActivity;
import com.firewok.example.java_integration.viewoptions.ViewOptionsMainActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button feedIntegrationBtn = findViewById(R.id.feedIntegrationBtn);
        feedIntegrationBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.firewok.example.java_integration.feedintegration.MainActivity.class);
            startActivity(intent);
        });

        Button storyBlockBtn = findViewById(R.id.storyBlockBtn);
        storyBlockBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.firewok.example.java_integration.storyblock.MainActivity.class);
            startActivity(intent);
        });

        Button pictureInPictureBtn = findViewById(R.id.pictureInPictureBtn);
        pictureInPictureBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.firewok.example.java_integration.pip.PictureInPictureActivity.class);
            startActivity(intent);
        });

        Button shareLinkBtn = findViewById(R.id.shareLinkBtn);
        shareLinkBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.firewok.example.java_integration.share.ShareLinkMainActivity.class);
            startActivity(intent);
        });

        Button languageBtn = findViewById(R.id.languageBtn);
        languageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.firewok.example.java_integration.language.LanguageMainActivity.class);
            startActivity(intent);
        });

        Button shoppingBtn = findViewById(R.id.shoppingBtn);
        shoppingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, com.firewok.example.java_integration.shopping.ShoppingMainActivity.class);
            startActivity(intent);
        });

        Button liveStreamBtn = findViewById(R.id.liveStreamBtn);
        liveStreamBtn.setOnClickListener(v -> {
            Intent intent = LiveMainActivity.getIntent(this);
            startActivity(intent);
        });

        Button feedResourceBtn = findViewById(R.id.feedResourceBtn);
        feedResourceBtn.setOnClickListener(v -> {
            Intent intent = FeedResourceMainActivity.getIntent(this);
            startActivity(intent);
        });

        Button viewOptionsBtn = findViewById(R.id.viewOptionsBtn);
        viewOptionsBtn.setOnClickListener(v -> {
            Intent intent = ViewOptionsMainActivity.getIntent(this);
            startActivity(intent);
        });
    }
}
