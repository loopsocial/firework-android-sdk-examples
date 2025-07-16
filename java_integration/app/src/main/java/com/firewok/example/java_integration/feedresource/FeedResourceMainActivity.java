package com.firewok.example.java_integration.feedresource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityFeedResourceMainBinding;
import com.firewok.example.java_integration.feedresource.channel.ChannelActivity;
import com.firewok.example.java_integration.feedresource.channelhashtags.ChannelHashtagsActivity;
import com.firewok.example.java_integration.feedresource.discovery.DiscoveryActivity;
import com.firewok.example.java_integration.feedresource.dynamiccontent.DynamicContentActivity;
import com.firewok.example.java_integration.feedresource.playlist.PlaylistActivity;
import com.firewok.example.java_integration.feedresource.singlecontent.SingleContentActivity;
import com.firewok.example.java_integration.feedresource.sku.SkuActivity;

public class FeedResourceMainActivity extends AppCompatActivity {
    private ActivityFeedResourceMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedResourceMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.feed_resource_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        binding.discovery.setOnClickListener(v -> {
            Intent intent = DiscoveryActivity.getIntent(this);
            startActivity(intent);
        });

        binding.playlist.setOnClickListener(v -> {
            Intent intent = PlaylistActivity.getIntent(this);
            startActivity(intent);
        });

        binding.channel.setOnClickListener(v -> {
            Intent intent = ChannelActivity.getIntent(this);
            startActivity(intent);
        });

        binding.dynamicContent.setOnClickListener(v -> {
            Intent intent = DynamicContentActivity.getIntent(this);
            startActivity(intent);
        });

        binding.channelHashtags.setOnClickListener(v -> {
            Intent intent = ChannelHashtagsActivity.getIntent(this);
            startActivity(intent);
        });

        binding.sku.setOnClickListener(v -> {
            Intent intent = SkuActivity.getIntent(this);
            startActivity(intent);
        });

        binding.singleElement.setOnClickListener(v -> {
            Intent intent = SingleContentActivity.getIntent(this);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, FeedResourceMainActivity.class);
    }
} 