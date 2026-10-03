package dev.localkeyboard.core

import android.content.Context

class SelectedLanguagesStore(context: Context) {
    private val prefs = context.getSharedPreferences("selected_languages", Context.MODE_PRIVATE)

    fun tags(): List<String> {
        val saved = prefs.getStringSet(KEY, null)?.toList().orEmpty()
        return if (saved.isEmpty()) listOf("ja", "en", "sv", "ar") else saved
    }

    fun select(tag: String) {
        val next = tags().toMutableSet().apply { add(tag) }
        prefs.edit().putStringSet(KEY, next).apply()
    }

    fun remove(tag: String) {
        val next = tags().toMutableSet().apply { remove(tag) }
        if (next.isNotEmpty()) prefs.edit().putStringSet(KEY, next).apply()
    }

    companion object { private const val KEY = "tags" }
}
