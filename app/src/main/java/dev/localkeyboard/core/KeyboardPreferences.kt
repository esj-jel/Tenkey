package dev.localkeyboard.core

import android.content.Context

class KeyboardPreferences(context: Context) {
    private val prefs = context.getSharedPreferences("keyboard_preferences", Context.MODE_PRIVATE)
    var haptics: Boolean
        get() = prefs.getBoolean("haptics", true)
        set(value) { prefs.edit().putBoolean("haptics", value).apply() }
    var keySound: Boolean
        get() = prefs.getBoolean("keySound", false)
        set(value) { prefs.edit().putBoolean("keySound", value).apply() }
}
