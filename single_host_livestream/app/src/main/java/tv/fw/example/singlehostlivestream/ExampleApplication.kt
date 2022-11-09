package tv.fw.example.singlehostlivestream

import android.app.Application
import android.util.Log
import tv.fw.example.singlehostlivestream.BuildConfig.FW_CLIENT_ID
import tv.fw.fireworksdk.FireworkSdk
import tv.fw.fireworksdk.FireworkSdkConfig
import tv.fw.imageloading.glide.GlideImageLoaderFactory
import tv.fw.livestream.singlehost.LivestreamPlayerInitializer

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // build Firework Android SDK v2 configuration
        val config = FireworkSdkConfig.Builder(this)
            .checksumRequired(false)
            .clientId(FW_CLIENT_ID) // Client OAUTH Id
            .userId("example app user ID") // User Id in your eco-system
            .imageLoader(GlideImageLoaderFactory.createInstance()) // glide, picasso, or your implementation
            .addLivestreamPlayerInitializer(LivestreamPlayerInitializer()) // Single-Host Livestream and Multi-Host Livestream can be used together, and single-host can be used as a fallback for missing multi-host
            .build()

        // initialize Firework Android SDK v2
        FireworkSdk.init(
            fireworkSdkConfig = config,
            onSuccess = {
                Log.i("FireworkSDK", "Firework SDK initialized successfully")
            },
            onError = { initError ->
                Log.e("FireworkSDK", "Error initializing Firework - $initError")
            }
        )
    }
}
