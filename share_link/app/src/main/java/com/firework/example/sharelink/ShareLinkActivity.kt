package com.firework.example.sharelink

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firework.example.sharelink.Constants.BASE_SHARE_URL
import com.firework.sdk.FireworkSdk
import com.firework.sdk.PlayerLaunchResult
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.viewOptions

class ShareLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewOptions =
            viewOptions {
                playerOptions {
                    shareBaseUrl(BASE_SHARE_URL)
                }
            }

        val url = intent.data?.toString()!!
        val playerLaunchResult = FireworkSdk.startPlayer(viewOptions = viewOptions, url = url)

        if (playerLaunchResult is PlayerLaunchResult.Failure) {
            Toast.makeText(this, playerLaunchResult.message, Toast.LENGTH_SHORT).show()
        }

        finish()
    }
}
