package com.firewok.example.java_integration.feedintegration.addremove;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityAddRemoveIntegrationBinding;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;

public class AddRemoveIntegrationActivity extends AppCompatActivity {
    private ActivityAddRemoveIntegrationBinding binding;
    private FwVideoFeedView videoFeedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRemoveIntegrationBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        setTitle(R.string.add_remove_screen_title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.add_remove_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        
        initAddRemoveButtons();
    }

    private void initAddRemoveButtons() {
        binding.addFeed.setOnClickListener(v -> {
            addFeed();
            updateButtonsAvailability();
        });

        binding.removeFeed.setOnClickListener(v -> {
            removeFeed();
            updateButtonsAvailability();
        });

        updateButtonsAvailability();
    }

    private void addFeed() {
        FwVideoFeedView videoFeedView = new FwVideoFeedView(this);

        ViewOptions viewOptions = new ViewOptions.Builder().baseOption(new BaseOption.Builder().feedResource(FeedResource.Discovery.INSTANCE).build()).build();


        videoFeedView.init(viewOptions);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        binding.feedContainer.addView(videoFeedView, layoutParams);

        this.videoFeedView = videoFeedView;
    }

    private void removeFeed() {
        if (videoFeedView != null) {
            binding.feedContainer.removeView(videoFeedView);
            videoFeedView.destroy();
            this.videoFeedView = null;
        }
    }

    private void updateButtonsAvailability() {
        boolean isFeedAdded = this.videoFeedView != null;
        binding.addFeed.setEnabled(!isFeedAdded);
        binding.removeFeed.setEnabled(isFeedAdded);
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
        if (videoFeedView != null) {
            videoFeedView.destroy();
        }
        super.onDestroy();
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, AddRemoveIntegrationActivity.class);
    }
} 