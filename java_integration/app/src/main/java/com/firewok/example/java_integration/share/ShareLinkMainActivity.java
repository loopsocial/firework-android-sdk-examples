package com.firewok.example.java_integration.share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.PlayerOption;
import com.firework.viewoptions.ViewOptions;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityShareLinkMainBinding;

public class ShareLinkMainActivity extends AppCompatActivity {
    
    private ActivityShareLinkMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShareLinkMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.share_link_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        initVideoFeedView();
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        ViewOptions viewOptions = new ViewOptions.Builder()
                .baseOption(new BaseOption.Builder()
                        .feedResource(FeedResource.Discovery.INSTANCE)
                        .build())
                .playerOption(new PlayerOption.Builder()
                        .showShareButton(true)
                        .shareBaseUrl(Constants.BASE_SHARE_URL)
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
        return new Intent(context, ShareLinkMainActivity.class);
    }
} 