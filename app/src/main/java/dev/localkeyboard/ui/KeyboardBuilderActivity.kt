package dev.localkeyboard.ui

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dev.localkeyboard.core.CustomKeyboardProfile
import dev.localkeyboard.core.CustomKeyboardProfileStore

class KeyboardBuilderActivity : AppCompatActivity() {
    private lateinit var language: EditText
    private lateinit var name: EditText
    private lateinit var rows: EditText
    private lateinit var status: TextView
    private val profileStore by lazy { CustomKeyboardProfileStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profile = profileStore.load()
        language = field("Language tag", profile.languageTag)
        name = field("Keyboard name", profile.displayName)
        rows = field("Rows (use | between rows)", profile.rows.joinToString("|"))
        status = TextView(this).apply { setTextColor(Color.DKGRAY); setPadding(0, 20, 0, 20) }
        val content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(28, 28, 28, 28) }
        content.addView(TextView(this).apply { text = "Custom keyboard studio"; textSize = 28f })
        content.addView(TextView(this).apply {
            text = "Create and save a custom layout locally. Kotoba does not require an account or send your text or settings to a server."
            setPadding(0, 12, 0, 20)
        })
        listOf(language, name, rows).forEach(content::addView)
        content.addView(Button(this).apply { text = "Save custom keyboard"; setOnClickListener { saveProfile() } })
        content.addView(status)
        setContentView(ScrollView(this).apply { addView(content) })
    }

    private fun saveProfile() {
        profileStore.save(CustomKeyboardProfile(language.text.toString().trim(), name.text.toString().trim(), rows.text.toString().split("|").map(String::trim).filter(String::isNotBlank)))
        status.text = "Saved on this device. Reopen the keyboard to apply it."
    }

    private fun field(hint: String, value: String) = EditText(this).apply {
        this.hint = hint
        setText(value)
        setPadding(0, 14, 0, 14)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
