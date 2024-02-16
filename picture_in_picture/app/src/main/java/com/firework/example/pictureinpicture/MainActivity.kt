package com.firework.example.pictureinpicture

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.error.player.PipEnterError
import com.firework.example.pictureinpicture.databinding.ActivityMainBinding
import com.firework.sdk.FireworkSdk
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.viewOptions

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupDetails()

        initVideoFeedView()

        setupPictureInPictureButtons()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Discovery"
        binding.details.channel.text = "N/A"
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.fwVideoFeedView

        val viewOptions =
            viewOptions {
                baseOptions {
                    // Check Channel, Playlist, or Dynamic screens for other feed sources
                    feedResource(FeedResource.Discovery)
                }

                playerOptions {
                    enablePipMode(true)
                }
            }

        videoFeedView.init(viewOptions)
    }

    private fun setupPictureInPictureButtons() {
        binding.enterPip.setOnClickListener {
            Toast.makeText(this@MainActivity, "Player will go to PiP mode in 5sec, you have time to open it", Toast.LENGTH_LONG).show()
            it.postDelayed(
                {
                    FireworkSdk.enterPip(
                        onSuccess = { isIgnored ->
                            if (!isIgnored) {
                                Log.i(TAG, "Successfully entered PiP")
                            } else {
                                Log.i(TAG, "Ignored entered PiP request since player is already in PiP mode")
                            }
                        },
                        onError = { pipEnterError ->
                            val cause =
                                when (pipEnterError) {
                                    PipEnterError.SdkIsNotInitialized -> "SDK is not initialized yet"
                                    PipEnterError.NoActivePlayer -> "there is no active player"
                                    PipEnterError.NotSupported -> "PiP feature is not supported by device"
                                    PipEnterError.DisabledByClient -> "PiP feature is not enabled in SDK config"
                                    PipEnterError.DisabledByUser -> "PiP feature is disabled by user"
                                }
                            Log.i(TAG, "Failed to enter PiP mode cause $cause")
                        },
                    )
                },
                ENTER_PIP_DELAY_MILLIS,
            )
        }

        binding.closePip.setOnClickListener {
            FireworkSdk.closePip()
        }
    }

    override fun onStop() {
        Log.i(TAG, "onStop - isFinishing: $isFinishing")
        if (binding.closePipOnStop.isChecked && isFinishing) {
            FireworkSdk.closePip()
        }
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy")
        if (binding.closePipOnDestroy.isChecked) {
            FireworkSdk.closePip()
        }
        binding.fwVideoFeedView.destroy()
        super.onDestroy()
    }

    companion object {
        private const val TAG = "Picture-in-picture"
        private const val ENTER_PIP_DELAY_MILLIS = 5_000L // 5 sec
    }
}
