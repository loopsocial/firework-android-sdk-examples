package com.firewok.example.java_integration.feedresource.sku;

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
import com.firewok.example.java_integration.databinding.ActivitySkuBinding;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;
import java.util.Arrays;
import java.util.List;

public class SkuActivity extends AppCompatActivity {
    private ActivitySkuBinding binding;
    private static final List<String> DEFAULT_IDS = Arrays.asList("testSku1", "testSku2");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySkuBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.sku_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupDetails();
        initVideoFeedView();

        binding.etSkuIds.setText(String.join(",", DEFAULT_IDS));
        binding.btnApplyFilter.setOnClickListener(v -> {
            String[] productIds = binding.etSkuIds.getText().toString().split(",");
            List<String> productIdList = Arrays.asList(productIds);
            updateViewOptions(productIdList);
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Sku");
        binding.details.channel.setText(BuildConfig.FW_CHANNEL_ID);
        binding.details.playlist.setText("N/A");
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        ViewOptions viewOptions = new ViewOptions.Builder()
            .baseOption(new BaseOption.Builder()
                .feedResource(new FeedResource.Sku(BuildConfig.FW_CHANNEL_ID, DEFAULT_IDS))
                .build())
            .build();

        videoFeedView.init(viewOptions);
    }

    private void updateViewOptions(List<String> productIds) {
        binding.fwVideoFeedView.destroy();
        binding.feedContainer.removeAllViews();
        
        FwVideoFeedView videoFeedView = new FwVideoFeedView(this);
        ViewOptions viewOptions = new ViewOptions.Builder()
            .baseOption(new BaseOption.Builder()
                .feedResource(new FeedResource.Sku(BuildConfig.FW_CHANNEL_ID, productIds))
                .build())
            .build();
        
        binding.feedContainer.addView(videoFeedView);
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

    public static Intent getIntent(Context context) {
        return new Intent(context, SkuActivity.class);
    }
} 