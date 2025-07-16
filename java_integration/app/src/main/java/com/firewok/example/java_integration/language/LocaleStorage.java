package com.firewok.example.java_integration.language;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Locale;

public class LocaleStorage {
    private static final String LOCALE_PREFERENCES_NAME = "locale_storage";
    private static final String LOCALE_KEY = "locale";

    private final SharedPreferences sharedPreferences;

    public LocaleStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(LOCALE_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public Locale getLocale() {
        String defaultLocaleLanguage = Locale.getDefault().getLanguage();
        String localeValue = sharedPreferences.getString(LOCALE_KEY, defaultLocaleLanguage);
        
        if (localeValue == null) {
            localeValue = defaultLocaleLanguage;
        }
        
        if (localeValue.contains("_")) {
            String[] locales = localeValue.split("_");
            if (locales.length > 1) {
                return new Locale(locales[0], locales[1]);
            }
        }
        return new Locale(localeValue);
    }

    public void setLocale(Locale locale) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOCALE_KEY, locale.toString());
        editor.apply();
    }
} 