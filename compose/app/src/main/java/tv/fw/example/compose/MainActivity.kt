package tv.fw.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import tv.fw.common.PlayerMode
import tv.fw.common.feed.FeedLayout
import tv.fw.common.feed.FeedResource
import tv.fw.common.feed.FeedTitlePosition
import tv.fw.example.compose.ui.theme.FireworkComposeTheme
import tv.fw.videofeed.VideoFeedView
import tv.fw.videofeed.options.ViewOptions

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
    val viewOptions = ViewOptions.Builder()
        .feedResource(FeedResource.Discovery)
        .feedLayout(FeedLayout.GRID)
        .columnCount(3)
        .backgroundColor(MaterialTheme.colors.background.hashCode())
        .itemSpacing(16)
        .roundedCorner(true)
        .roundedCornerRadius(10)
        .showFeedTitle(true)
        .feedTitleTextColor(MaterialTheme.colors.primary.hashCode())
        .feedTitlePosition(FeedTitlePosition.NESTED)
        .feedTitleTextPadding(8)
        .feedTitleTextSize(48)
        .feedTitleTextNumberOfLines(1)
        .playerMode(PlayerMode.FIT_MODE)
        .showPlayIcon(true)
        .muteOnLaunch(true)
        .showShareButton(true)
        .autoPlayOnComplete(true)
        .build()

    AndroidView(factory = { context ->
        VideoFeedView(context).apply {
            init(viewOptions)
        }
    })
}
