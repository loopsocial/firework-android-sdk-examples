package tv.fw.example.discovery

import android.app.Application
import tv.fw.example.discovery.BuildConfig.FW_CLIENT_ID
import tv.fw.fireworksdk.FireworkSdk
import tv.fw.fireworksdk.FireworkSdkConfig
import tv.fw.imageloading.glide.GlideImageLoaderFactory

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // build Firework Android SDK v2 configuration
        val config = FireworkSdkConfig.Builder(this)
            .checksumRequired(false)
            .clientId(FW_CLIENT_ID) // Client OAUTH Id
            .userId("example app user ID") // User Id in your eco-system
            .imageLoader(GlideImageLoaderFactory.createInstance()) // glide, picasso, or your implementation
            .build()

        // initialize Firework Android SDK v2
        FireworkSdk.init(config)
    }
}