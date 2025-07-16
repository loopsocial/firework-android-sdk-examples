package com.firewok.example.java_integration.share;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.sdk.FireworkSdk;
import com.firework.sdk.PlayerLaunchResult;
import com.firework.viewoptions.PlayerOption;
import com.firework.viewoptions.ViewOptions;

public class ShareLinkActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewOptions viewOptions = new ViewOptions.Builder()
                .playerOption(new PlayerOption.Builder()
                        .shareBaseUrl(Constants.BASE_SHARE_URL)
                        .build())
                .build();

        Uri data = getIntent().getData();
        if (data != null) {
            String url = data.toString();
            PlayerLaunchResult playerLaunchResult = FireworkSdk.INSTANCE.startPlayer(viewOptions, url);

            if (playerLaunchResult instanceof PlayerLaunchResult.Failure) {
                PlayerLaunchResult.Failure failure = (PlayerLaunchResult.Failure) playerLaunchResult;
                Toast.makeText(this, failure.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
} 