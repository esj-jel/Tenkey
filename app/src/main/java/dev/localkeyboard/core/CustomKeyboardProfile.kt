package dev.localkeyboard.core

import android.content.Context

/** User-owned layout/theme profile. Stored locally and applied by the IME on next start. */
data class CustomKeyboardProfile(
    val languageTag: String = "ja",
    val displayName: String = "Japanese Tenkey",
    val rows: List<String> = listOf("あ か さ た な", "は ま や ら わ"),
    val backgroundStart: Int = 0xFFF7F2FF.toInt(),
    val backgroundEnd: Int = 0xFFE9F5FF.toInt(),
    val keyColor: Int = 0xFFFFFFFF.toInt(),
    val accentColor: Int = 0xFF6750A4.toInt()
)

class CustomKeyboardProfileStore(context: Context) {
    private val prefs = context.getSharedPreferences("custom_keyboard_profile", Context.MODE_PRIVATE)

    fun load(): CustomKeyboardProfile = CustomKeyboardProfile(
        languageTag = prefs.getString("languageTag", "ja") ?: "ja",
        displayName = prefs.getString("displayName", "Japanese Tenkey") ?: "Japanese Tenkey",
        rows = prefs.getString("rows", "あ か さ た な|は ま や ら わ")!!.split("|").filter(String::isNotBlank),
        backgroundStart = prefs.getInt("backgroundStart", 0xFFF7F2FF.toInt()),
        backgroundEnd = prefs.getInt("backgroundEnd", 0xFFE9F5FF.toInt()),
        keyColor = prefs.getInt("keyColor", 0xFFFFFFFF.toInt()),
        accentColor = prefs.getInt("accentColor", 0xFF6750A4.toInt())
    )

    fun save(profile: CustomKeyboardProfile) {
        prefs.edit()
            .putString("languageTag", profile.languageTag)
            .putString("displayName", profile.displayName)
            .putString("rows", profile.rows.joinToString("|"))
            .putInt("backgroundStart", profile.backgroundStart)
            .putInt("backgroundEnd", profile.backgroundEnd)
            .putInt("keyColor", profile.keyColor)
            .putInt("accentColor", profile.accentColor)
            .apply()
    }
}
