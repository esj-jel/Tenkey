package dev.localkeyboard.input

import android.inputmethodservice.InputMethodService
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import dev.localkeyboard.core.ClipboardRepository

class LocalKeyboardService : InputMethodService(), KeyboardView.Callbacks {
    private lateinit var keyboard: KeyboardView
    private lateinit var clipboard: ClipboardRepository
    private var state = KeyboardState()
    private var lastInput: InputConnection? = null

    override fun onCreate() { super.onCreate(); clipboard = ClipboardRepository(this) }

    override fun onCreateInputView(): View {
        keyboard = KeyboardView(this, this)
        val height = (300f * resources.displayMetrics.density).toInt()
        keyboard.minimumHeight = height
        keyboard.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height)
        keyboard.render(state)
        return keyboard
    }

    override fun onStartInput(attribute: EditorInfo?, restarting: Boolean) {
        super.onStartInput(attribute, restarting)
        lastInput = currentInputConnection
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
    override fun onJapaneseMode() { state = state.copy(japaneseMode = if (state.japaneseMode == JapaneseMode.HIRAGANA) JapaneseMode.KATAKANA else JapaneseMode.HIRAGANA); keyboard.render(state) }
    override fun onUtility(panel: UtilityPanel) { state = state.copy(utilityPanel = if (state.utilityPanel == panel) UtilityPanel.NONE else panel); keyboard.render(state) }
    override fun onSpaceCursor(delta: Int) {
        if (delta == 0) return
        val connection = currentInputConnection ?: return
        val extracted = connection.getExtractedText(ExtractedTextRequest(), 0) ?: return
        val current = extracted.selectionStart.coerceAtLeast(0)
        val target = (current + delta).coerceIn(0, extracted.text.length)
        connection.setSelection(target, target)
    }
}
