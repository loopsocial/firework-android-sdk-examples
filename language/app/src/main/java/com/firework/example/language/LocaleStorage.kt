package com.firework.example.language

import android.content.Context
import androidx.core.content.edit
import java.util.Locale

class LocaleStorage(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(LOCALE_PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun getLocale(): Locale {
        val defaultLocaleLanguage: String = Locale.getDefault().language.toString()
        val localeValue: String = sharedPreferences.getString(LOCALE_KEY, defaultLocaleLanguage) ?: defaultLocaleLanguage
        if (localeValue.contains("_")) {
            val locales = localeValue.split("_")
            if (locales.size > 1) {
                return Locale(locales[0], locales[1])
            }
        }
        return Locale(localeValue)
    }

    fun setLocale(locale: Locale) {
        sharedPreferences.edit { putString(LOCALE_KEY, locale.toString()) }
    }

    companion object {
        private const val LOCALE_PREFERENCES_NAME = "locale_storage"
        private const val LOCALE_KEY = "locale"
    }
}
