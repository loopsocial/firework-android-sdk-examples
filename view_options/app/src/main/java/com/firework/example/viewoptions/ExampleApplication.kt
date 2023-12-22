package com.firework.example.viewoptions

import android.app.Application
import android.util.Log
import com.firework.example.viewoptions.BuildConfig.FW_CLIENT_ID
import com.firework.imageloading.glide.GlideImageLoaderFactory
import com.firework.sdk.FireworkSdk
import com.firework.sdk.FireworkSdkConfig

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // build Firework Android SDK v6 configuration
        val config =
            FireworkSdkConfig.Builder(this)
                .checksumRequired(false)
                .clientId(FW_CLIENT_ID) // Client OAUTH Id
                .userId("example app user ID") // User Id in your eco-system
                .imageLoader(GlideImageLoaderFactory.createInstance(context = this)) // glide, picasso, or your implementation
                .muteOnLaunch(true)
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
