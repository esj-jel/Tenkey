package dev.localkeyboard.core

import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import java.io.File

class UserFontStore(private val context: Context) {
    private val fontFile get() = File(context.filesDir, "fonts/user-keyboard.ttf")

    fun importFrom(uri: Uri): Boolean = runCatching {
        fontFile.parentFile?.mkdirs()
        context.contentResolver.openInputStream(uri).use { input ->
            requireNotNull(input)
            fontFile.outputStream().use { output -> input.copyTo(output) }
        }
        true
    }.getOrDefault(false)

    fun load(): Typeface? = if (fontFile.exists()) Typeface.createFromFile(fontFile) else null
    fun clear() { fontFile.delete() }
}
