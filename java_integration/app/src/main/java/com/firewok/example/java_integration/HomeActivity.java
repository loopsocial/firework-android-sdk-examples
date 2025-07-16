package com.firewok.example.java_integration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.feedintegration.MainActivity;

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
    }
}
