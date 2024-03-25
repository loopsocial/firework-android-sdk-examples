package com.firework.example.shopping

import android.app.Application
import com.firework.example.shopping.BuildConfig.FW_CLIENT_ID
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
                .imageLoader(GlideImageLoaderFactory.createInstance(context = this)) // glide, picasso, or your implementation
                .build()

        // initialize Firework Android SDK v6
        FireworkSdk.init(config)
    }
}
