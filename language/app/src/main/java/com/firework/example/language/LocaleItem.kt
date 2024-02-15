package com.firework.example.language

import java.util.Locale

class LocaleItem(
    private val languageName: String,
    val locale: Locale,
) {
    override fun toString(): String {
        return languageName
    }
}
