package com.firewok.example.java_integration.feedintegration.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityActivityIntegrationBinding;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;

public class ActivityIntegrationActivity extends AppCompatActivity {
    private ActivityActivityIntegrationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityActivityIntegrationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        setTitle(R.string.activity_screen_title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.activity_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        
        initVideoFeedView();
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        ViewOptions viewOptions = new ViewOptions.Builder().baseOption(new BaseOption.Builder().feedResource(FeedResource.Discovery.INSTANCE).build()).build();


        videoFeedView.init(viewOptions);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        binding.fwVideoFeedView.destroy();
        super.onDestroy();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ActivityIntegrationActivity.class);
    }
} 