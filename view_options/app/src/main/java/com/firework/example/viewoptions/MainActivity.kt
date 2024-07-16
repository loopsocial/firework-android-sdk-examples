package com.firework.example.viewoptions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.firework.common.PlayerMode
import com.firework.common.ad.AdBadgeTextType
import com.firework.common.cta.CtaDelay
import com.firework.common.cta.CtaDelayUnit
import com.firework.common.cta.CtaStyle
import com.firework.common.feed.FeedLayout
import com.firework.common.feed.FeedResource
import com.firework.common.feed.FeedTitlePosition
import com.firework.common.widget.ActionButton
import com.firework.common.widget.Shape
import com.firework.common.widget.WidgetImage
import com.firework.example.viewoptions.databinding.ActivityMainBinding
import com.firework.videofeed.FwVideoFeedView
import com.firework.viewoptions.ViewOptions
import com.firework.viewoptions.actionButtonOptions
import com.firework.viewoptions.adBadgeOptions
import com.firework.viewoptions.adOptions
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.ctaOptions
import com.firework.viewoptions.layoutOptions
import com.firework.viewoptions.pipButtonOptions
import com.firework.viewoptions.playerOptions
import com.firework.viewoptions.playerUiOptions
import com.firework.viewoptions.titleOptions
import com.firework.viewoptions.viewOptions

@Suppress("MagicNumber")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val videoFeedView: FwVideoFeedView
        get() = binding.videoFeedView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupDetails()

        initVideoFeedView()

        setupVideoFeedViewCallbacks()
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Discovery"
        binding.details.channel.text = "N/A"
        binding.details.playlist.text = "N/A"
    }

    private fun initVideoFeedView() {
        /**
         * Initialization method 1:
         * - Using [FwVideoFeedView]'s attributes in the XML layout file by calling [VideoFeedView.init] with no parameters
         *
         * Initialization method 2:
         * - Using [ViewOptions] Builders at runtime
         *
         * Initialization method 3:
         * - Using [viewOptions] Kotlin DSL methods at runtime
         */

        val viewOptions =
            viewOptions {
                adBadgeOptions {
                    adBadgeBackColor(getContextCompatColor(R.color.cyan))
                    adBadgeIsHidden(false)
                    adBadgeLabel(AdBadgeTextType.SPONSORED)
                    adBadgeShowOnThumbnails(true)
                    adBadgeTextColor(getContextCompatColor(R.color.white))
                    adBadgeTypeface(getContextCompatFont(R.font.roboto_black))
                }

                adOptions {
                    adsFetchTimeoutInSeconds(15)
                    vastAttributes(mapOf())
                }

                baseOptions {
                    feedResource(FeedResource.Discovery)
                }

                ctaOptions {
                    ctaDelay(CtaDelay(0.2f, CtaDelayUnit.PERCENTAGE))
                    ctaStyle(
                        CtaStyle(
                            shape = Shape.SHAPE_OVAL,
                            backgroundColor = getContextCompatColor(R.color.purple_200),
                            textColor = getContextCompatColor(R.color.cyan),
                            fontSize = spToPx(18f).toFloat(),
                        ),
                    )
                }

                layoutOptions {
                    backgroundColor(getContextCompatColor(R.color.black))
                    columnCount(3)
                    feedLayout(FeedLayout.GRID)
                    feedTitlePosition(FeedTitlePosition.NESTED)
                    itemSpacing(dpToPx(4f))
                    playIconWidth(dpToPx(24f))
                    roundedCorner(true)
                    roundedCornerRadius(dpToPx(16f))
                    showPlayIcon(true)
                }

                playerOptions {
                    autoPlayOnComplete(true)
                    autoplay(true)
                    enablePipMode(true)
                    playerMode(PlayerMode.FIT_MODE)
                    showFireworkLogo(true)
                    showMuteButton(true)
                    showPlayPauseButtonInReplay(true)
                    showShareButton(true)
                    playerUiOptions {
                        pipButtonOptions {
                            icon(WidgetImage(R.drawable.ic_pip))
                        }
                    }
                    actionButtonOptions {
                        actionButton(
                            ActionButton(
                                backgroundColor = getContextCompatColor(R.color.purple_200),
                                dividingLineColor = getContextCompatColor(R.color.cyan),
                                shape = Shape.SHAPE_OVAL,
                                textColor = getContextCompatColor(R.color.black),
                            ),
                        )
                        cancelButton(
                            ActionButton(
                                backgroundColor = getContextCompatColor(R.color.teal_200),
                                shape = Shape.SHAPE_OVAL,
                                textColor = getContextCompatColor(R.color.purple_200),
                            ),
                        )
                    }
                }

                titleOptions {
                    feedTitleBackgroundColor(getContextCompatColor(R.color.purple_transparent))
                    // This is an example of how to set a gradient background for the feed title, Note: This will get priority over the above color
                    // feedTitleBackgroundDrawable(
                    //     GradientDrawable(
                    //         GradientDrawable.Orientation.TOP_BOTTOM,
                    //         arrayOf(Color.BLUE, Color.CYAN, Color.MAGENTA).toIntArray()
                    //     ),
                    // )
                    feedTitleTextColor(getContextCompatColor(R.color.black))
                    feedTitleTextNumberOfLines(1)
                    feedTitleTextPadding(dpToPx(8f))
                    feedTitleTextSize(spToPx(14f))
                    feedTitleTextTypeface(getContextCompatFont(R.font.roboto_regular))
                    showFeedTitle(true)
                }
            }

        videoFeedView.init(viewOptions)
    }

    private fun getContextCompatColor(
        @ColorRes colorId: Int,
    ) = ContextCompat.getColor(this, colorId)

    private fun getContextCompatFont(
        @FontRes fontId: Int,
    ) = ResourcesCompat.getFont(this, fontId)!!

    private fun setupVideoFeedViewCallbacks() {
        // All errors related to this video feed view can be collected using this callback to handle
        videoFeedView.setOnErrorListener { fwError ->
            Log.e("FireworkSDK", "Firework VideoFeedView error: $fwError")
        }

        // All feed item clicks related to this video feed view can be collected using this callback
        // to maybe trigger some analytics event
        videoFeedView.setOnFeedItemClickListener { feedItem ->
            Log.e("FireworkSDK", "On feed item clicked: $feedItem")
        }
    }

    companion object {
        private fun Context.spToPx(sp: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.displayMetrics).toInt()
        }

        private fun Context.dpToPx(dp: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
        }
    }
}
