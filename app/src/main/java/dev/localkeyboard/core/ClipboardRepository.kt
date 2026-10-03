package dev.localkeyboard.core

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class ClipboardRepository(context: Context) {
    private val appContext = context.applicationContext
    private val clipboard = context.getSystemService(ClipboardManager::class.java)
    private val history = ArrayDeque<String>()

    fun captureCurrent() {
        val text = clipboard.primaryClip?.getItemAt(0)?.coerceToText(appContext)?.toString() ?: return
        if (text.isBlank()) return
        history.remove(text)
        history.addFirst(text)
        while (history.size > 20) history.removeLast()
    }

    fun items(): List<String> = history.toList()
    fun copy(text: String) = clipboard.setPrimaryClip(ClipData.newPlainText("Local Keyboard", text))
}
