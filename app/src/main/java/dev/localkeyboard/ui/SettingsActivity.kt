package dev.localkeyboard.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.localkeyboard.core.KeyboardPreferences
import dev.localkeyboard.core.LanguageCatalog
import dev.localkeyboard.core.UserFontStore

class SettingsActivity : AppCompatActivity() {
    private lateinit var status: TextView
    private val preferences by lazy { KeyboardPreferences(this) }
    private val fontStore by lazy { UserFontStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(28, 28, 28, 28) }
        root.addView(TextView(this).apply { text = "Tenkey"; textSize = 30f; setTextColor(Color.rgb(45, 38, 55)) })
        root.addView(TextView(this).apply { text = "A private, playful Japanese-first keyboard inspired by Florisboard."; textSize = 16f; setPadding(0, 12, 0, 20) })
        root.addView(button("Enable keyboard") { startActivity(Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)) })
        root.addView(button("Choose keyboard") { (getSystemService(INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager).showInputMethodPicker() })
        root.addView(button("Custom keyboard studio") { startActivity(Intent(this, KeyboardBuilderActivity::class.java)) })
        root.addView(button("How to use Tenkey") { startActivity(Intent(this, TutorialActivity::class.java)) })
        root.addView(button("Import your font") { chooseFont() })
        root.addView(CheckBox(this).apply { text = "Haptic key feedback"; isChecked = preferences.haptics; setOnCheckedChangeListener { _, checked -> preferences.haptics = checked } })
        root.addView(CheckBox(this).apply { text = "Key sounds"; isChecked = preferences.keySound; setOnCheckedChangeListener { _, checked -> preferences.keySound = checked } })
        root.addView(TextView(this).apply { text = "Languages included: ${LanguageCatalog.all.size}"; gravity = Gravity.CENTER; setPadding(0, 22, 0, 0) })
        status = TextView(this).apply { setTextColor(Color.DKGRAY); setPadding(0, 18, 0, 0) }
        root.addView(status)
        setContentView(ScrollView(this).apply { addView(root) })
    }

    private fun chooseFont() {
        startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "font/*"; addCategory(Intent.CATEGORY_OPENABLE); putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("font/ttf", "font/otf", "application/octet-stream"))
        }, REQUEST_FONT)
    }

    @Deprecated("Android calls this after the font picker returns")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_FONT && resultCode == RESULT_OK && data?.data != null) {
            status.text = if (fontStore.importFrom(data.data!!)) "Font imported locally. Reopen the keyboard to apply it." else "Could not import that font."
        }
    }

    private fun button(label: String, action: () -> Unit) = Button(this).apply { text = label; setOnClickListener { action() } }

    companion object { private const val REQUEST_FONT = 42 }
}
