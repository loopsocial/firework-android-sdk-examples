package tv.fw.sharelink

import android.app.Application
import android.util.Log
import tv.fw.fireworksdk.FireworkSdk
import tv.fw.fireworksdk.FireworkSdkConfig
import tv.fw.imageloading.glide.GlideImageLoaderFactory

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
            },
        )
    }

    companion object {
        private const val BASE_SHARE_URL = "https://example.com/"
    }
}
