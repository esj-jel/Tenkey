package dev.localkeyboard.input

import android.inputmethodservice.InputMethodService
import android.view.View
import android.view.inputmethod.EditorInfo
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
        keyboard.minimumHeight = 320
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
    override fun onGlobe() { switchToNextInputMethod(false) }
    override fun onJapaneseMode() { state = state.copy(japaneseMode = if (state.japaneseMode == JapaneseMode.HIRAGANA) JapaneseMode.KATAKANA else JapaneseMode.HIRAGANA); keyboard.render(state) }
    override fun onUtility(panel: UtilityPanel) { state = state.copy(utilityPanel = if (state.utilityPanel == panel) UtilityPanel.NONE else panel); keyboard.render(state) }
    override fun onSpaceCursor(delta: Int) { currentInputConnection?.setSelection(delta, delta) }
}
