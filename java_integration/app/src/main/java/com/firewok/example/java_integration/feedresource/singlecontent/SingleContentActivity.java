package com.firewok.example.java_integration.feedresource.singlecontent;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firewok.example.java_integration.BuildConfig;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivitySingleContentBinding;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;

public class SingleContentActivity extends AppCompatActivity {
    private ActivitySingleContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingleContentBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.single_content_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupDetails();
        initVideoFeedView();
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("SingleContent");
        binding.details.elementId.setText(BuildConfig.FW_CONTENT_ID);
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        ViewOptions viewOptions = new ViewOptions.Builder()
            .baseOption(new BaseOption.Builder()
                .feedResource(new FeedResource.SingleContent(BuildConfig.FW_CONTENT_ID))
                .build())
            .build();

        videoFeedView.init(viewOptions);
    }

    @Override
    protected void onDestroy() {
        binding.fwVideoFeedView.destroy();
        super.onDestroy();
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
        return new Intent(context, SingleContentActivity.class);
    }
} 