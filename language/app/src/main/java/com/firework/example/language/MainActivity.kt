package com.firework.example.language

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.firework.common.feed.FeedResource
import com.firework.example.language.BuildConfig.FW_CHANNEL_ID
import com.firework.example.language.BuildConfig.FW_PLAYLIST_ID
import com.firework.example.language.databinding.ActivityMainBinding
import com.firework.viewoptions.baseOptions
import com.firework.viewoptions.viewOptions
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var localeStorage: LocaleStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        setupLanguageSpinner()

        setupDetails()

        initVideoFeedView()
    }

    private fun setupLanguageSpinner() {
        val languageSpinner = binding.languageSpinner

        val adapter: ArrayAdapter<LocaleItem> = ArrayAdapter(this, android.R.layout.simple_spinner_item, supportedLocaleItems)
        languageSpinner.adapter = adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        languageSpinner.setSelection(adapter.getPosition(supportedLocaleItems.firstOrNull { it.locale == localeStorage.getLocale() }), false)

        languageSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long,
                ) {
                    val selectedLocale = supportedLocaleItems[position].locale
                    localeStorage.setLocale(selectedLocale)
                    recreate()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private fun setupDetails() {
        binding.details.source.text = "Playlist"
        binding.details.channel.text = FW_CHANNEL_ID
        binding.details.playlist.text = FW_PLAYLIST_ID
    }

    private fun initVideoFeedView() {
        val videoFeedView = binding.videoFeedView

        // Replace this playlist with one including SingleHost Livestream
        val playlistFeedResource = FeedResource.Playlist(FW_CHANNEL_ID, FW_PLAYLIST_ID)

        val viewOptions =
            viewOptions {
                baseOptions {
                    feedResource(playlistFeedResource)
                }
            }

        videoFeedView.init(viewOptions)
    }

    override fun onDestroy() {
        val videoFeedView = binding.videoFeedView
        videoFeedView.destroy()
        super.onDestroy()
    }

    override fun attachBaseContext(newBase: Context) {
        localeStorage = LocaleStorage(context = newBase)
        super.attachBaseContext(updatedLocaleContext(newBase))
    }

    private fun updatedLocaleContext(context: Context): Context {
        val resources = context.resources
        val configuration = resources.configuration
        val locale = localeStorage.getLocale()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    companion object {
        private val supportedLocaleItems: List<LocaleItem> =
            listOf(
                LocaleItem("English", Locale("en")),
                LocaleItem("Arabic", Locale("ar")),
                LocaleItem("Spanish", Locale("es")),
                LocaleItem("Persian", Locale("fa")),
                LocaleItem("Japanese", Locale("ja")),
                LocaleItem("Polish", Locale("pl")),
                LocaleItem("Portuguese", Locale("pt")),
            )
    }
}
