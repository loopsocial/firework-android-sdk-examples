package com.firework.example.compose

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.firework.common.PlayerMode
import com.firework.common.cta.CtaDelay
import com.firework.common.cta.CtaDelayUnit
import com.firework.common.cta.CtaStyle
import com.firework.common.feed.FeedLayout
import com.firework.common.feed.FeedResource
import com.firework.common.feed.FeedTitlePosition
import com.firework.common.widget.ActionButton
import com.firework.common.widget.Shape
import com.firework.common.widget.WidgetImage
import com.firework.videofeed.fwVideoFeedView
import com.firework.videofeed.viewOptions
import com.firework.viewoptions.actionButtonOptions
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.ctaOptions
import com.firework.viewoptions.layoutOptions
import com.firework.viewoptions.pipButtonOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.playerUiOptions
import com.firework.viewoptions.titleOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
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
    val feedTitleTextColor = MaterialTheme.colorScheme.onPrimary.hashCode()
    val feedTitleBackgroundColor = MaterialTheme.colorScheme.primary.hashCode()
    val backgroundColor = MaterialTheme.colorScheme.background.hashCode()
    val secondaryColor = MaterialTheme.colorScheme.secondary.toArgb()

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
                ctaOptions {
                    ctaDelay(CtaDelay(0.2f, CtaDelayUnit.PERCENTAGE))
                    ctaStyle(
                        CtaStyle(
                            shape = Shape.SHAPE_OVAL,
                            backgroundColor = secondaryColor,
                            textColor = feedTitleTextColor,
                            fontSize = context.spToPx(18f).toFloat(),
                        ),
                    )
                }
                playerOptions {
                    playerMode(PlayerMode.FIT_MODE)
                    showShareButton(true)
                    autoPlayOnComplete(true)
                    playerUiOptions {
                        pipButtonOptions {
                            icon(WidgetImage(R.drawable.ic_pip))
                        }
                    }
                    actionButtonOptions {
                        actionButton(
                            ActionButton(
                                backgroundColor = secondaryColor,
                                dividingLineColor = backgroundColor,
                                shape = Shape.SHAPE_OVAL,
                                textColor = feedTitleTextColor,
                            ),
                        )
                        cancelButton(
                            ActionButton(
                                backgroundColor = secondaryColor,
                                shape = Shape.SHAPE_OVAL,
                                textColor = feedTitleTextColor,
                            ),
                        )
                    }
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
