package com.firework.example.compose.storyblock

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.firework.common.PlayerMode
import com.firework.common.feed.FeedResource
import com.firework.storyblock.FwStoryBlockView
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.storyBlockOptions
import com.firework.viewoptions.viewOptions
import kotlin.apply
import kotlin.collections.getOrPut

class ComposeLazyVerticalGridStoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyVerticalGridItem(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Suppress("MagicNumber")
val list = List(50) {
    if (it == 3 || it == 24) {
        "View"
    } else {
        "Text"
    }
}

private val width = 150.dp
private val height = 250.dp

@Composable
fun LazyVerticalGridItem(modifier: Modifier = Modifier) {
    val androidViewMap = remember { mutableMapOf<String, FwStoryBlockView>() }
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(list.size) { index ->
            if (list[index] == "View") {
                StoryBlockItem(modifier = modifier, key = "View_${index}", androidViewMap = androidViewMap)
            } else {
                Text(text = "Text $index", modifier = modifier.size(width, height))
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            for (v in androidViewMap.values) {
                v.destroy()
            }
        }
    }
}

@Composable
fun StoryBlockItem(modifier: Modifier = Modifier, key: String, androidViewMap: MutableMap<String, FwStoryBlockView>) {
    val context = LocalContext.current
    val storyblockAndroidView = androidViewMap.getOrPut(key) {
        val storyBlockView = FwStoryBlockView(context = LocalContext.current)
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
        storyBlockView.init(
            (context as AppCompatActivity).supportFragmentManager,
            context,
            viewOptions,
            true,
        )
        storyBlockView.apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
            storyBlockView.setOnErrorListener { e ->
                Log.d("firework", "$e")
            }
        }
    }

    AndroidView(
        modifier = modifier
            .padding(top = 16.dp)
            .size(width, height),
        factory = { _ ->
            storyblockAndroidView
        },
        update = { _ ->
            Log.d("firework", "update")
        },
        onReset = {
            Log.d("firework", "reset")
        },
        onRelease = {
            Log.d("firework", "release")
        },
    )
}