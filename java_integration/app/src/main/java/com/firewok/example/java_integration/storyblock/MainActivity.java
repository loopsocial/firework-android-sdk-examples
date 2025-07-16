package com.firewok.example.java_integration.storyblock;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.firewok.example.java_integration.R;
import com.firework.common.PlayerMode;
import com.firework.common.feed.FeedResource;
import com.firework.storyblock.FwStoryBlockView;
import com.firework.viewoptions.PlayerOption;
import com.firework.viewoptions.StoryBlockOption;
import com.firework.viewoptions.ViewOptions;
import com.firework.viewoptions.BaseOption;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private FwStoryBlockView storyBlock;
    private MaterialButton btnPlay;
    private MaterialButton btnPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storyblock_main);

        // Initialize views
        storyBlock = findViewById(R.id.story_block);
        btnPlay = findViewById(R.id.btn_play);
        btnPause = findViewById(R.id.btn_pause);

        // Initialize StoryBlock
        ViewOptions viewOptions = new ViewOptions.Builder()
                .baseOption(new BaseOption.Builder()
                        .feedResource(FeedResource.Discovery.INSTANCE)
                        .build())
                .playerOption(new PlayerOption.Builder().playerMode(PlayerMode.FIT_MODE).build())
                .build();

        storyBlock.init(
                getSupportFragmentManager(),
                this,
                viewOptions,
                true // pauseWhenNotVisible
        );

        // Set error listener
        storyBlock.setOnErrorListener(error -> {
            Log.i("StoryBlock-Fragment", error.toString());
        });

        // Set feed load listener
        storyBlock.setFeedLoadListener(feedLoadState -> {
            Log.i("StoryBlock", feedLoadState.toString());
        });

        // Set button click listeners
        btnPlay.setOnClickListener(v -> storyBlock.play());
        btnPause.setOnClickListener(v -> storyBlock.pause());
    }
} 