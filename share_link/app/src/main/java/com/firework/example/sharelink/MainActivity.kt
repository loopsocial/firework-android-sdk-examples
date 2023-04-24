package com.firework.example.sharelink

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.sharelink.Constants.BASE_SHARE_URL
import com.firework.example.sharelink.databinding.ActivityMainBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.viewOptions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val viewOptions = viewOptions {
            baseOptions {
                feedResource(FeedResource.Discovery)
            }
            playerOptions {
                showShareButton(true)
                shareBaseUrl(BASE_SHARE_URL)
            }
        }

        binding.fwVideoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }
}
