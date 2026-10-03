package dev.localkeyboard.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.localkeyboard.core.LanguageCatalog

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 32, 32, 32) }
        root.addView(TextView(this).apply { text = "Local Keyboard"; textSize = 28f })
        root.addView(TextView(this).apply { text = "Offline-first Japanese tenkey keyboard\nDefault: Hiragana • 55 language metadata entries • Material You styling"; textSize = 16f; setPadding(0, 16, 0, 24) })
        root.addView(Button(this).apply { text = "Enable keyboard"; setOnClickListener { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) } })
        root.addView(Button(this).apply { text = "Choose keyboard"; setOnClickListener { (getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager).showInputMethodPicker() } })
        root.addView(TextView(this).apply { text = "Languages included: ${LanguageCatalog.all.size}"; gravity = Gravity.CENTER; setPadding(0, 24, 0, 0) })
        setContentView(root)
    }
}
