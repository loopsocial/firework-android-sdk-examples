package com.firework.example.compose.storyblock

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.firework.common.PlayerMode
import com.firework.common.feed.FeedResource
import com.firework.storyblock.FwStoryBlockView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.storyBlockOptions
import com.firework.viewoptions.viewOptions
import kotlin.apply

class ComposeStoryActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StoryblockItem(
                        modifier =
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun StoryblockItem(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val storyBlockView = FwStoryBlockView(context)
    // init view options
    val viewOptions = viewOptions {
        baseOptions {
            feedResource(FeedResource.Discovery)
        }
        playerOptions {
            playerMode(PlayerMode.FIT_MODE)
        }
        storyBlockOptions {
            enableAutoPlay(true)
            showFullScreenIcon(true)
        }
    }

    AndroidView(
        modifier = modifier.padding(start = 20.dp, top = 50.dp, end = 20.dp, bottom = 50.dp),
        factory = { _ ->
            storyBlockView.init(
                (context as FragmentActivity).supportFragmentManager,
                context,
                viewOptions,
                true
            )
            storyBlockView.apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                setOnErrorListener { e ->
                    Log.d("firework", "$e")
                }
            }
        },
        update = {
            Log.d("firework", "update")
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            Log.d("firework", "dispose")
            storyBlockView.destroy()
        }
    }
}
