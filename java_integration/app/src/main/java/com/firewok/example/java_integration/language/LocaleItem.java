package com.firewok.example.java_integration.language;

import java.util.Locale;

public class LocaleItem {
    private final String languageName;
    private final Locale locale;

    public LocaleItem(String languageName, Locale locale) {
        this.languageName = languageName;
        this.locale = locale;
    }

    public String getLanguageName() {
        return languageName;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return languageName;
    }
} 