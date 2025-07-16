package com.firewok.example.java_integration.feedresource.dynamiccontent;

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
import com.firewok.example.java_integration.databinding.ActivityDynamicContentBinding;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicContentActivity extends AppCompatActivity {
    private ActivityDynamicContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDynamicContentBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.dynamic_content_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupDetails();
        initVideoFeedView();
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Dynamic");
        binding.details.channel.setText(BuildConfig.FW_CHANNEL_ID);
        binding.details.playlist.setText("N/A");
        binding.details.categories.setText("Test category");
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        List<String> categories = Arrays.asList("Test category");
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("category", categories);
        
        FeedResource.DynamicContent feedResource = new FeedResource.DynamicContent(
            BuildConfig.FW_CHANNEL_ID, 
            parameters
        );
        
        ViewOptions viewOptions = new ViewOptions.Builder()
            .baseOption(new BaseOption.Builder()
                .feedResource(feedResource)
                .build())
            .build();

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
        return new Intent(context, DynamicContentActivity.class);
    }
} 