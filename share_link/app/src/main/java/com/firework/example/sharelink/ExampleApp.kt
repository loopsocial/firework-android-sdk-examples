package com.firework.example.sharelink

import android.app.Application
import android.util.Log
import com.firework.imageloading.glide.GlideImageLoaderFactory
import com.firework.sdk.FireworkSdk
import com.firework.sdk.FireworkSdkConfig

class ExampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = FireworkSdkConfig
            .Builder(this)
            .checksumRequired(false)
            .clientId(BuildConfig.FW_CLIENT_ID)
            .enableCache(true)
            .shareBaseUrl(BASE_SHARE_URL)
            .imageLoader(GlideImageLoaderFactory.createInstance())
            .build()
        FireworkSdk.init(
            config,
            onSuccess = {
                Log.i("XXXX-init", "Init Successful!")
            },
            onError = { error ->
                Log.e("XXXX-init", "Init Failed: $error")
            }
        )
    }

    companion object {
        // Replace this with your website domain -->
        private const val BASE_SHARE_URL = "https://example.com/"
    }
}
