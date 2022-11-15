package tv.fw.example.compose

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
    val feedTitleTextColor = MaterialTheme.colors.primary.hashCode()
    val backgroundColor = MaterialTheme.colors.background.hashCode()

    AndroidView(factory = { context ->
        val viewOptions = ViewOptions.Builder()
            .feedResource(FeedResource.Discovery)
            .feedLayout(FeedLayout.GRID)
            .columnCount(3)
            .backgroundColor(backgroundColor)
            .itemSpacing(context.dpToPx(16f))
            .roundedCorner(true)
            .roundedCornerRadius(context.dpToPx(16f))
            .showFeedTitle(true)
            .feedTitleTextColor(feedTitleTextColor)
            .feedTitlePosition(FeedTitlePosition.NESTED)
            .feedTitleTextPadding(context.dpToPx(8f))
            .feedTitleTextSize(context.spToPx(14f))
            .feedTitleTextNumberOfLines(1)
            .playerMode(PlayerMode.FIT_MODE)
            .showPlayIcon(true)
            .muteOnLaunch(true)
            .showShareButton(true)
            .autoPlayOnComplete(true)
            .build()

        VideoFeedView(context).apply {
            init(viewOptions)
        }
    })
}

private fun Context.spToPx(sp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
}

private fun Context.dpToPx(dp: Float): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}
