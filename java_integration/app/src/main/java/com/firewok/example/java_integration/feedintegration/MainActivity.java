package com.firewok.example.java_integration.feedintegration;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.feedintegration.activity.ActivityIntegrationActivity;
import com.firewok.example.java_integration.feedintegration.addremove.AddRemoveIntegrationActivity;
import com.firewok.example.java_integration.databinding.ActivityMainBinding;
import com.firewok.example.java_integration.feedintegration.fragment.FragmentIntegrationActivity;
import com.firewok.example.java_integration.feedintegration.recyclerview.RecyclerViewIntegrationActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.activityFeed.setOnClickListener(v -> {
            Intent intent = ActivityIntegrationActivity.getIntent(this);
            startActivity(intent);
        });

        binding.fragmentFeed.setOnClickListener(v -> {
            Intent intent = FragmentIntegrationActivity.getIntent(this);
            startActivity(intent);
        });

        binding.recyclerViewFeed.setOnClickListener(v -> {
            Intent intent = RecyclerViewIntegrationActivity.getIntent(this);
            startActivity(intent);
        });

        binding.addRemoveFeed.setOnClickListener(v -> {
            Intent intent = AddRemoveIntegrationActivity.getIntent(this);
            startActivity(intent);
        });
    }
}