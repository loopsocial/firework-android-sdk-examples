package com.firework.example.multihostlivestream

import android.app.Application
import android.util.Log
import com.firework.example.multihostlivestream.BuildConfig.FW_CLIENT_ID
import com.firework.imageloading.glide.GlideImageLoaderFactory
import com.firework.livestream.multihost.MultiHostLivestreamPlayerInitializer
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
                .imageLoader(GlideImageLoaderFactory.createInstance(context = this)) // glide, picasso, or your implementation
                // Single-Host and Multi-Host Livestreams can be used together, and single-host will be used as a fallback if multi-host is missing
                .addLivestreamPlayerInitializer(MultiHostLivestreamPlayerInitializer())
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
