package com.firework.example.storyblock

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.PlayerMode
import com.firework.common.feed.FeedResource
import com.firework.example.storyblock.databinding.ActivityMainBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.viewOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.storyBlock.init(
            supportFragmentManager,
            lifecycle,
            viewOptions {
                baseOptions {
                    feedResource(FeedResource.Discovery)
                }
                playerOptions {
                    playerMode(PlayerMode.FIT_MODE)
                }
            },
            binding.storyBlockContainer,
        )
        binding.storyBlock.setOnErrorListener {
            Log.i("StoryBlock-Fragment", it.toString())
        }
        binding.btnPlay.setOnClickListener {
            binding.storyBlock.play()
        }

        binding.btnPause.setOnClickListener {
            binding.storyBlock.pause()
        }
        binding.storyBlock.setFeedLoadListener {
            Log.i("StoryBlock", it.toString())
        }
    }
}
