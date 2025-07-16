package com.firewok.example.java_integration;

import static com.firewok.example.java_integration.BuildConfig.FW_CLIENT_ID;

import android.app.Application;
import android.util.Log;

import com.firework.imageloading.glide.GlideImageLoaderFactory;
import com.firework.sdk.FireworkSdk;
import com.firework.sdk.FireworkSdkConfig;

public class ExampleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FireworkSdkConfig config = new FireworkSdkConfig.Builder(this)
                .clientId(FW_CLIENT_ID) // Client OAUTH Id
                .userId("example app user ID") // User Id in your eco-system
                .imageLoader(GlideImageLoaderFactory.INSTANCE.createInstance(this)) // glide, picasso, or your implementation
                .build();

        FireworkSdk.init(
                config,
                () -> {
                    Log.i("FireworkSDK", "Firework SDK initialized successfully");
                    return null;
                },
                (initError) -> {
                    Log.e("FireworkSDK", "Error initializing Firework - " + initError);
                    return null;
                }
        );
    }
}
