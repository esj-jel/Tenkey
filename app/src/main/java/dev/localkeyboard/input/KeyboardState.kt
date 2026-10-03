package dev.localkeyboard.input

enum class JapaneseMode { HIRAGANA, KATAKANA }
enum class UtilityPanel { NONE, EMOJI, KAOMOJI, CLIPBOARD, DEVTOOLS }

data class KeyboardState(
    val languageTag: String = "ja",
    val japaneseMode: JapaneseMode = JapaneseMode.HIRAGANA,
    val utilityPanel: UtilityPanel = UtilityPanel.NONE,
    val shift: Boolean = false,
    val composing: String = ""
)
