package com.firework.example.feedresources

import android.app.Application
import android.util.Log
import com.firework.example.feedresources.BuildConfig.FW_CLIENT_ID
import com.firework.imageloading.glide.GlideImageLoaderFactory
import com.firework.sdk.FireworkSdk
import com.firework.sdk.FireworkSdkConfig

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // build Firework Android SDK v6 configuration
        val config =
            FireworkSdkConfig.Builder(this)
                .clientId(FW_CLIENT_ID) // Client OAUTH Id
                .userId("example app user ID") // User Id in your eco-system
                .imageLoader(GlideImageLoaderFactory.createInstance(this)) // glide, picasso, or your implementation
                .build()

        // initialize Firework Android SDK v6
        FireworkSdk.init(
            fireworkSdkConfig = config,
            onSuccess = {
                Log.i("FireworkSDK", "Firework SDK initialized successfully")
            },
            onError = { initError ->
                Log.e("FireworkSDK", "Error initializing Firework - $initError")
            },
        )
    }
}
