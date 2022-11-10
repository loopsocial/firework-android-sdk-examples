package tv.fw.example.multihostlivestream

import android.app.Application
import android.util.Log
import tv.fw.example.multihostlivestream.BuildConfig.FW_CLIENT_ID
import tv.fw.fireworksdk.FireworkSdk
import tv.fw.fireworksdk.FireworkSdkConfig
import tv.fw.imageloading.glide.GlideImageLoaderFactory
import tv.fw.livestream.multihostsupport.MultihostSupportLivestreamPlayerInitializer

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // build Firework Android SDK v2 configuration
        val config = FireworkSdkConfig.Builder(this)
            .checksumRequired(false)
            .clientId(FW_CLIENT_ID) // Client OAUTH Id
            .userId("example app user ID") // User Id in your eco-system
            .imageLoader(GlideImageLoaderFactory.createInstance()) // glide, picasso, or your implementation
            // Single-Host and Multi-Host Livestreams can be used together, and single-host will be used as a fallback if multi-host is missing
            .addLivestreamPlayerInitializer(MultihostSupportLivestreamPlayerInitializer())
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
