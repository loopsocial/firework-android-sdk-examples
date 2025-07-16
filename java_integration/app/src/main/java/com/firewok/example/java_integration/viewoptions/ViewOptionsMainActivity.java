package com.firewok.example.java_integration.viewoptions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import androidx.annotation.ColorRes;
import androidx.annotation.FontRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.firework.common.PlayerMode;
import com.firework.common.ad.AdBadgeOption;
import com.firework.common.ad.AdBadgeTextType;
import com.firework.common.ad.AdOption;
import com.firework.common.cta.CtaDelay;
import com.firework.common.cta.CtaDelayUnit;
import com.firework.common.cta.CtaStyle;
import com.firework.common.feed.FeedLayout;
import com.firework.common.feed.FeedResource;
import com.firework.common.feed.FeedTitlePosition;
import com.firework.common.player.ActionButtonOption;
import com.firework.common.player.PipButtonOption;
import com.firework.common.player.PlayerUiOption;
import com.firework.common.widget.ActionButton;
import com.firework.common.widget.Shape;
import com.firework.common.widget.WidgetImage;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityViewOptionsMainBinding;
import com.firework.videofeed.FwVideoFeedView;

import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.CtaOption;
import com.firework.viewoptions.LayoutOption;

import com.firework.viewoptions.PlayerOption;

import com.firework.viewoptions.TitleOption;
import com.firework.viewoptions.ViewOptions;
import java.util.HashMap;
import java.util.Map;

public class ViewOptionsMainActivity extends AppCompatActivity {
    private ActivityViewOptionsMainBinding binding;

    private FwVideoFeedView getVideoFeedView() {
        return binding.videoFeedView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewOptionsMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.view_options_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupDetails();
        initVideoFeedView();
        setupVideoFeedViewCallbacks();
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Discovery");
        binding.details.channel.setText("N/A");
        binding.details.playlist.setText("N/A");
    }

    private void initVideoFeedView() {
        // Ad Badge Options
        AdBadgeOption adBadgeOption = new AdBadgeOption.Builder()
            .adBadgeBackColor(getContextCompatColor(R.color.purple_200))
            .adBadgeIsHidden(false)
            .adBadgeLabel(AdBadgeTextType.SPONSORED)
            .adBadgeShowOnThumbnails(true)
            .adBadgeTextColor(getContextCompatColor(android.R.color.white))
            .adBadgeTypeface(getContextCompatFont(R.font.roboto_black))
            .build();

        // Ad Options
        Map<String, String> vastAttributes = new HashMap<>();
        AdOption adOption = new AdOption.Builder()
            .adsFetchTimeoutInSeconds(15)
            .vastAttributes(vastAttributes)
            .build();

        // Base Options
        BaseOption baseOption = new BaseOption.Builder()
            .feedResource(FeedResource.Discovery.INSTANCE)
            .build();

        // CTA Options
        CtaStyle ctaStyle = new CtaStyle(
            Shape.SHAPE_OVAL,
            getContextCompatColor(R.color.purple_200),
            getContextCompatColor(R.color.teal_200),
            (float) spToPx(18f)
        );
        CtaOption ctaOption = new CtaOption.Builder()
            .ctaDelay(new CtaDelay(0.2f, CtaDelayUnit.PERCENTAGE))
            .ctaStyle(ctaStyle)
            .build();

        // Layout Options
        LayoutOption layoutOption = new LayoutOption.Builder()
            .backgroundColor(getContextCompatColor(android.R.color.black))
            .columnCount(3)
            .feedLayout(FeedLayout.GRID)
            .feedTitlePosition(FeedTitlePosition.NESTED)
            .itemSpacing(dpToPx(4f))
            .playIconWidth(dpToPx(24f))
            .roundedCorner(true)
            .roundedCornerRadius(dpToPx(16f))
            .showPlayIcon(true)
            .build();

        // PiP Button Options
        PipButtonOption pipButtonOption = new PipButtonOption.Builder()
            .icon(new WidgetImage(R.drawable.ic_pip,null))
            .build();

        // Player UI Options
        PlayerUiOption playerUiOption = new PlayerUiOption.Builder()
            .pipButtonOption(pipButtonOption)
            .build();

        // Action Button Options
        ActionButton actionButton = new ActionButton(
            getContextCompatColor(R.color.purple_200),
            getContextCompatColor(R.color.teal_200),
            Shape.SHAPE_OVAL,
            getContextCompatColor(android.R.color.black)
        );
        ActionButton cancelButton = new ActionButton(
            getContextCompatColor(R.color.teal_200),
            0, // No dividing line color
            Shape.SHAPE_OVAL,
            getContextCompatColor(R.color.purple_200)
        );
        ActionButtonOption actionButtonOption = new ActionButtonOption.Builder()
            .actionButton(actionButton)
            .cancelButton(cancelButton)
            .build();

        // Player Options
        PlayerOption playerOption = new PlayerOption.Builder()
            .autoPlayOnComplete(true)
            .autoplay(true)
            .enablePipMode(true)
            .playerMode(PlayerMode.FIT_MODE)
            .showFireworkLogo(true)
            .showMuteButton(true)
            .showPlayPauseButtonInReplay(true)
            .showShareButton(true)
            .playerUiOption(playerUiOption)
            .actionButtonOption(actionButtonOption)
            .build();

        // Title Options
        TitleOption titleOption = new TitleOption.Builder()
            .feedTitleBackgroundColor(getContextCompatColor(R.color.purple_200))
            // This is an example of how to set a gradient background for the feed title, Note: This will get priority over the above color
            // .feedTitleBackgroundDrawable(
            //     new GradientDrawable(
            //         GradientDrawable.Orientation.TOP_BOTTOM,
            //         new int[]{Color.BLUE, Color.CYAN, Color.MAGENTA}
            //     )
            // )
            .feedTitleTextColor(getContextCompatColor(android.R.color.black))
            .feedTitleTextNumberOfLines(1)
            .feedTitleTextPadding(dpToPx(8f))
            .feedTitleTextSize(spToPx(14f))
            .feedTitleTextTypeface(getContextCompatFont(R.font.roboto_regular))
            .showFeedTitle(true)
            .build();

        // Build ViewOptions
        ViewOptions viewOptions = new ViewOptions.Builder()
            .adBadgeOption(adBadgeOption)
            .adOption(adOption)
            .baseOption(baseOption)
            .ctaOption(ctaOption)
            .layoutOption(layoutOption)
            .playerOption(playerOption)
            .titleOption(titleOption)
            .build();

        getVideoFeedView().init(viewOptions);
    }

    private int getContextCompatColor(@ColorRes int colorId) {
        return ContextCompat.getColor(this, colorId);
    }

    private android.graphics.Typeface getContextCompatFont(@FontRes int fontId) {
        return ResourcesCompat.getFont(this, fontId);
    }

    private void setupVideoFeedViewCallbacks() {
        // All errors related to this video feed view can be collected using this callback to handle
        getVideoFeedView().setOnErrorListener(fwError -> {
            Log.e("FireworkSDK", "Firework VideoFeedView error: " + fwError);
        });

        // All feed item clicks related to this video feed view can be collected using this callback
        // to maybe trigger some analytics event
        getVideoFeedView().setOnFeedItemClickListener(feedItem -> {
            Log.e("FireworkSDK", "On feed item clicked: " + feedItem);
        });
    }

    private int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context) {
        return new Intent(context, ViewOptionsMainActivity.class);
    }
} 