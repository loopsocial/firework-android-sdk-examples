package tv.fw.sharelink

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tv.fw.fireworksdk.FireworkSdk
import tv.fw.fireworksdk.PlayerLaunchResult
import tv.fw.videofeed.options.ViewOptions

class ShareLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewOptions = ViewOptions.Builder().build()
        val url = intent.data?.toString()!!
        val playerLaunchResult = FireworkSdk.startPlayer(viewOptions = viewOptions, url = url)

        if (playerLaunchResult is PlayerLaunchResult.Failure) {
            Toast.makeText(this, playerLaunchResult.message, Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
