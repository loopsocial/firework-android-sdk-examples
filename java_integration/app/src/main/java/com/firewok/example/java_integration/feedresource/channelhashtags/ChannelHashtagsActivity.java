package com.firewok.example.java_integration.feedresource.channelhashtags;

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
import com.firewok.example.java_integration.databinding.ActivityChannelHashtagsBinding;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;

public class ChannelHashtagsActivity extends AppCompatActivity {
    private ActivityChannelHashtagsBinding binding;
    
    /**
     * Hashtag filter expression is an s-expression used to provide feeds filtered by hashtags with specified criteria.
     * Queries are specified with boolean predicates on what hashtags are there on the video.
     * For instance, (and sport food) (or sport food) (and sport (or food comedy)) sport are all valid expressions.
     * Non-UTF-8 characters are not allowed.
     * If using boolean predicates, the expression needs to be wrapped with parenthesis.
     */
    private static final String HASHTAG_FILTER_EXPRESSION = "(or food art cats pets beauty fashion travel)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChannelHashtagsBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.channel_hashtags_screen_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupDetails();
        initVideoFeedView();

        binding.etHashtagFilter.setText(HASHTAG_FILTER_EXPRESSION);
        binding.btnApplyFilter.setOnClickListener(v -> {
            updateViewOptions(binding.etHashtagFilter.getText().toString());
        });
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Channel");
        binding.details.channel.setText(BuildConfig.FW_CHANNEL_ID);
        binding.details.playlist.setText("N/A");
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        ViewOptions viewOptions = new ViewOptions.Builder()
            .baseOption(new BaseOption.Builder()
                .feedResource(new FeedResource.ChannelHashtag(BuildConfig.FW_CHANNEL_ID, HASHTAG_FILTER_EXPRESSION))
                .build())
            .build();

        videoFeedView.init(viewOptions);
    }

    private void updateViewOptions(String hashTagFilter) {
        binding.fwVideoFeedView.destroy();
        binding.feedContainer.removeAllViews();
        
        FwVideoFeedView videoFeedView = new FwVideoFeedView(this);
        ViewOptions viewOptions = new ViewOptions.Builder()
            .baseOption(new BaseOption.Builder()
                .feedResource(new FeedResource.ChannelHashtag(BuildConfig.FW_CHANNEL_ID, hashTagFilter))
                .build())
            .build();
        
        binding.feedContainer.addView(videoFeedView);
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
        return new Intent(context, ChannelHashtagsActivity.class);
    }
} 