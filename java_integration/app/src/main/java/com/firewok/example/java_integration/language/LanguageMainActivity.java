package com.firewok.example.java_integration.language;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.firework.common.feed.FeedResource;
import com.firework.videofeed.FwVideoFeedView;
import com.firework.viewoptions.BaseOption;
import com.firework.viewoptions.ViewOptions;
import com.firewok.example.java_integration.BuildConfig;
import com.firewok.example.java_integration.R;
import com.firewok.example.java_integration.databinding.ActivityLanguageMainBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class LanguageMainActivity extends AppCompatActivity {

    private ActivityLanguageMainBinding binding;
    private LocaleStorage localeStorage;

    private static final List<LocaleItem> SUPPORTED_LOCALE_ITEMS = Arrays.asList(
            new LocaleItem("English", new Locale("en")),
            new LocaleItem("Arabic", new Locale("ar")),
            new LocaleItem("Arabic (Saudi Arabia)", new Locale("ar", "SA")),
            new LocaleItem("Arabic (United Arab Emirates)", new Locale("ar", "AE")),
            new LocaleItem("German", new Locale("de")),
            new LocaleItem("Italian", new Locale("it")),
            new LocaleItem("Japanese", new Locale("ja")),
            new LocaleItem("Polish", new Locale("pl")),
            new LocaleItem("Portuguese (Brazil)", new Locale("pt", "BR")),
            new LocaleItem("Russian", new Locale("ru")),
            new LocaleItem("Spanish", new Locale("es")),
            new LocaleItem("Spanish (Mexico)", new Locale("es", "MX")),
            new LocaleItem("Spanish (Colombia)", new Locale("es", "CO")),
            new LocaleItem("Vietnamese", new Locale("vi")),
            new LocaleItem("Thailand", new Locale("th")),
            new LocaleItem("Persian", new Locale("fa"))
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.language_title));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setupLanguageSpinner();
        setupDetails();
        initVideoFeedView();
    }

    private void setupLanguageSpinner() {
        Spinner languageSpinner = binding.languageSpinner;

        ArrayAdapter<LocaleItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SUPPORTED_LOCALE_ITEMS);
        languageSpinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Find the current locale and set selection
        LocaleItem currentLocaleItem = findLocaleItem(localeStorage.getLocale());
        if (currentLocaleItem != null) {
            int position = adapter.getPosition(currentLocaleItem);
            languageSpinner.setSelection(position, false);
        }

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Locale selectedLocale = SUPPORTED_LOCALE_ITEMS.get(position).getLocale();
                localeStorage.setLocale(selectedLocale);
                recreate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private LocaleItem findLocaleItem(Locale targetLocale) {
        for (LocaleItem item : SUPPORTED_LOCALE_ITEMS) {
            if (item.getLocale().toString().equalsIgnoreCase(targetLocale.toString())) {
                return item;
            }
        }
        return null;
    }

    @SuppressLint("SetTextI18n")
    private void setupDetails() {
        binding.details.source.setText("Playlist");
        binding.details.channel.setText(BuildConfig.FW_CHANNEL_ID);
        binding.details.playlist.setText(BuildConfig.FW_PLAYLIST_ID);
    }

    private void initVideoFeedView() {
        FwVideoFeedView videoFeedView = binding.videoFeedView;

        // Replace this playlist with one including SingleHost Livestream
        FeedResource playlistFeedResource = new FeedResource.Playlist(BuildConfig.FW_CHANNEL_ID, BuildConfig.FW_PLAYLIST_ID);

        ViewOptions viewOptions = new ViewOptions.Builder()
                .baseOption(new BaseOption.Builder()
                        .feedResource(playlistFeedResource)
                        .build())
                .build();

        videoFeedView.init(viewOptions);
    }

    @Override
    protected void onDestroy() {
        FwVideoFeedView videoFeedView = binding.videoFeedView;
        videoFeedView.destroy();
        super.onDestroy();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        localeStorage = new LocaleStorage(newBase);
        super.attachBaseContext(updatedLocaleContext(newBase));
    }

    private Context updatedLocaleContext(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = localeStorage.getLocale();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        return context.createConfigurationContext(configuration);
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
        return new Intent(context, LanguageMainActivity.class);
    }
} 