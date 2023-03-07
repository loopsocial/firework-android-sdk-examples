package com.firework.example.compose

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.firework.common.PlayerMode
import com.firework.common.feed.FeedLayout
import com.firework.common.feed.FeedResource
import com.firework.common.feed.FeedTitlePosition
import com.firework.example.compose.ui.theme.FireworkComposeTheme
import com.firework.videofeed.fwVideoFeedView
import com.firework.videofeed.viewOptions
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.layoutOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.titleOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FireworkComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    VideoFeed()
                }
            }
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun VideoFeed() {
    val feedTitleTextColor = MaterialTheme.colors.onPrimary.hashCode()
    val feedTitleBackgroundColor = MaterialTheme.colors.primary.hashCode()
    val backgroundColor = MaterialTheme.colors.background.hashCode()

    AndroidView(factory = { context ->
        fwVideoFeedView(context) {
            viewOptions {
                baseOptions {
                    feedResource(FeedResource.Discovery)
                }
                layoutOptions {
                    feedLayout(FeedLayout.GRID)
                    columnCount(3)
                    backgroundColor(backgroundColor)
                    itemSpacing(context.dpToPx(8f))
                    roundedCorner(true)
                    roundedCornerRadius(context.dpToPx(6f))
                    feedTitlePosition(FeedTitlePosition.NESTED)
                    showPlayIcon(true)
                }
                titleOptions {
                    showFeedTitle(true)
                    feedTitleBackgroundColor(feedTitleBackgroundColor)
                    feedTitleTextColor(feedTitleTextColor)
                    feedTitleTextPadding(context.dpToPx(8f))
                    feedTitleTextSize(context.spToPx(14f))
                    feedTitleTextNumberOfLines(1)
                }
                playerOptions {
                    playerMode(PlayerMode.FIT_MODE)
                    showShareButton(true)
                    autoPlayOnComplete(true)
                }
            }
        }
    })
}

private fun Context.spToPx(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics)
        .toInt()
}

private fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
        .toInt()
}
