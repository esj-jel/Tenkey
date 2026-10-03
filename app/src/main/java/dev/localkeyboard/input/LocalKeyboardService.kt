package dev.localkeyboard.input

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import dev.localkeyboard.core.ClipboardRepository
import dev.localkeyboard.core.CustomKeyboardProfile
import dev.localkeyboard.core.CustomKeyboardProfileStore
import dev.localkeyboard.core.LanguageCatalog
import dev.localkeyboard.core.SelectedLanguagesStore
import dev.localkeyboard.core.UserFontStore
import dev.localkeyboard.ui.SettingsActivity

class LocalKeyboardService : InputMethodService(), KeyboardView.Callbacks {
    private lateinit var keyboard: KeyboardView
    private lateinit var clipboard: ClipboardRepository
    private lateinit var profileStore: CustomKeyboardProfileStore
    private lateinit var languagesStore: SelectedLanguagesStore
    private var state = KeyboardState()
    private var profile: CustomKeyboardProfile = CustomKeyboardProfile()

    override fun onCreate() {
        super.onCreate()
        clipboard = ClipboardRepository(this)
        profileStore = CustomKeyboardProfileStore(this)
        languagesStore = SelectedLanguagesStore(this)
        profile = profileStore.load()
        state = state.copy(languageTag = profile.languageTag)
    }

    override fun onCreateInputView(): View {
        keyboard = KeyboardView(this, this)
        val height = (300f * resources.displayMetrics.density).toInt()
        keyboard.minimumHeight = height
        keyboard.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        keyboard.render(state)
        keyboard.renderProfile(profile)
        keyboard.renderTypeface(UserFontStore(this).load())
        return keyboard
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        state = state.copy(composing = "")
    }

    override fun onText(text: String) { currentInputConnection?.commitText(text, 1) }
    override fun onDelete() { currentInputConnection?.deleteSurroundingText(1, 0) }
    override fun onEnter() { currentInputConnection?.sendKeyEvent(android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_ENTER)) }

    override fun onGlobe() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            switchToNextInputMethod(false)
        } else {
            getSystemService(android.view.inputmethod.InputMethodManager::class.java).showInputMethodPicker()
        }
    }

    override fun onLanguagePopup() {
        val panel = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 20, 24, 20)
            background = GradientDrawable().apply { setColor(Color.WHITE); cornerRadius = 28f }
        }
        panel.addView(TextView(this).apply {
            text = "Languages"
            textSize = 20f
            setTextColor(Color.rgb(45, 38, 55))
            setPadding(0, 0, 0, 12)
        })
        val popup = PopupWindow(panel, (280 * resources.displayMetrics.density).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT, true).apply {
            elevation = 14f
            isOutsideTouchable = true
        }
        languagesStore.tags().mapNotNull { tag -> LanguageCatalog.all.find { it.tag == tag } }.forEach { language ->
            panel.addView(Button(this).apply {
                text = if (language.tag == state.languageTag) "✓  ${language.displayName}" else language.displayName
                setOnClickListener {
                    state = state.copy(languageTag = language.tag)
                    keyboard.render(state)
                    popup.dismiss()
                }
            })
        }
        panel.addView(Button(this).apply {
            text = "Manage languages"
            setOnClickListener {
                startActivity(Intent(this@LocalKeyboardService, SettingsActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                popup.dismiss()
            }
        })
        popup.showAtLocation(keyboard, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, keyboard.height + 12)
    }

    override fun onJapaneseMode() {
        state = state.copy(japaneseMode = if (state.japaneseMode == JapaneseMode.HIRAGANA) JapaneseMode.KATAKANA else JapaneseMode.HIRAGANA)
        keyboard.render(state)
    }

    override fun onUtility(panel: UtilityPanel) {
        state = state.copy(utilityPanel = if (state.utilityPanel == panel) UtilityPanel.NONE else panel)
        keyboard.render(state)
    }

    override fun onSpaceCursor(delta: Int) {
        if (delta == 0) return
        val connection: InputConnection = currentInputConnection ?: return
        val extracted = connection.getExtractedText(ExtractedTextRequest(), 0) ?: return
        val current = extracted.selectionStart.coerceAtLeast(0)
        val target = (current + delta).coerceIn(0, extracted.text.length)
        connection.setSelection(target, target)
    }
}
