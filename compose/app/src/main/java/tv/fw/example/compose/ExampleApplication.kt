package tv.fw.example.compose

import android.app.Application
import android.util.Log
import com.firework.imageloading.glide.GlideImageLoaderFactory
import com.firework.sdk.FireworkSdk
import com.firework.sdk.FireworkSdkConfig
import tv.fw.example.compose.BuildConfig.FW_CLIENT_ID

class ExampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // build Firework Android SDK v2 configuration
        val config = FireworkSdkConfig.Builder(this)
            .checksumRequired(false)
            .clientId(FW_CLIENT_ID) // Client OAUTH Id
            .userId("example app user ID") // User Id in your eco-system
            .imageLoader(GlideImageLoaderFactory.createInstance()) // glide, picasso, or your ImageLoader implementation
            .muteOnLaunch(true)
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
