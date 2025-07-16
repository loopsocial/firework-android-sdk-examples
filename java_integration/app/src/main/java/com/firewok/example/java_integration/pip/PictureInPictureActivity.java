package com.firewok.example.java_integration.pip;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firework.error.player.PipEnterError;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityPictureInPictureBinding;
import com.firework.sdk.FireworkSdk;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.PlayerOption;
import com.firework.viewoptions.ViewOptions;

public class PictureInPictureActivity extends AppCompatActivity {
    private static final String TAG = "Picture-in-picture";
    private static final long ENTER_PIP_DELAY_MILLIS = 5000L; // 5 sec

    private ActivityPictureInPictureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPictureInPictureBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.picture_in_picture_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupDetails();
        initVideoFeedView();
        setupPictureInPictureButtons();
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Discovery");
        binding.details.channel.setText("N/A");
        binding.details.playlist.setText("N/A");
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.fwVideoFeedView;

        ViewOptions viewOptions = new ViewOptions.Builder()
                .baseOption(new BaseOption.Builder()
                        .feedResource(FeedResource.Discovery.INSTANCE)
                        .build())
                .playerOption(new PlayerOption.Builder()
                        .enablePipMode(true)
                        .build())
                .build();

        videoFeedView.init(viewOptions);
    }

    private void setupPictureInPictureButtons() {
        binding.enterPip.setOnClickListener(v -> {
            Toast.makeText(PictureInPictureActivity.this, 
                    "Player will go to PiP mode in 5sec, you have time to open it", 
                    Toast.LENGTH_LONG).show();
            v.postDelayed(() -> {
                FireworkSdk.INSTANCE.enterPip(
                        isIgnored -> {
                            if (!isIgnored) {
                                Log.i(TAG, "Successfully entered PiP");
                            } else {
                                Log.i(TAG, "Ignored entered PiP request since player is already in PiP mode");
                            }
                            return null;
                        },
                        pipEnterError -> {
                            String cause;
                            if (pipEnterError == PipEnterError.SdkIsNotInitialized.INSTANCE) {
                                cause = "SDK is not initialized yet";
                            } else if (pipEnterError == PipEnterError.NoActivePlayer.INSTANCE) {
                                cause = "there is no active player";
                            } else if (pipEnterError == PipEnterError.NotSupported.INSTANCE) {
                                cause = "PiP feature is not supported by device";
                            } else if (pipEnterError == PipEnterError.DisabledByClient.INSTANCE) {
                                cause = "PiP feature is not enabled in SDK config";
                            } else if (pipEnterError == PipEnterError.DisabledByUser.INSTANCE) {
                                cause = "PiP feature is disabled by user";
                            } else if (pipEnterError == PipEnterError.NoPlayingPlayer.INSTANCE) {
                                cause = "there is no playing player";
                            } else {
                                cause = "unknown error";
                            }
                            Log.i(TAG, "Failed to enter PiP mode cause " + cause);
                            return null;
                        }
                );
            }, ENTER_PIP_DELAY_MILLIS);
        });

        binding.closePip.setOnClickListener(v -> {
            FireworkSdk.INSTANCE.closePip();
        });
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop - isFinishing: " + isFinishing());
        if (binding.closePipOnStop.isChecked() && isFinishing()) {
            FireworkSdk.INSTANCE.closePip();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy");
        if (binding.closePipOnDestroy.isChecked()) {
            FireworkSdk.INSTANCE.closePip();
        }
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
        return new Intent(context, PictureInPictureActivity.class);
    }
} 