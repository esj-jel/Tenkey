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
import dev.localkeyboard.core.AiKeyboardDesigner
import dev.localkeyboard.core.CustomKeyboardProfile
import dev.localkeyboard.core.CustomKeyboardProfileStore
import dev.localkeyboard.core.SecureApiKeyStore

class KeyboardBuilderActivity : AppCompatActivity() {
    private lateinit var language: EditText
    private lateinit var name: EditText
    private lateinit var rows: EditText
    private lateinit var apiKey: EditText
    private lateinit var prompt: EditText
    private lateinit var status: TextView
    private val profileStore by lazy { CustomKeyboardProfileStore(this) }
    private val keyStore by lazy { SecureApiKeyStore(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val profile = profileStore.load()
        language = field("Language tag", profile.languageTag)
        name = field("Keyboard name", profile.displayName)
        rows = field("Rows (use | between rows)", profile.rows.joinToString("|"))
        apiKey = field("AI API key (stored encrypted on this device)", "")
        apiKey.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        prompt = field("Ask the AI designer", "Create a friendly tenkey layout with visible punctuation")
        status = TextView(this).apply { setTextColor(Color.DKGRAY); setPadding(0, 20, 0, 20) }

        val content = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(28, 28, 28, 28) }
        content.addView(TextView(this).apply { text = "Custom keyboard studio"; textSize = 28f })
        content.addView(TextView(this).apply { text = "Build a private layout for any language. AI is optional; your key is encrypted locally and sent only to the provider you choose."; setPadding(0, 12, 0, 20) })
        listOf(language, name, rows, apiKey, prompt).forEach(content::addView)
        content.addView(Button(this).apply { text = "Save custom keyboard"; setOnClickListener { saveProfile() } })
        content.addView(Button(this).apply { text = "Generate with AI"; setOnClickListener { generate() } })
        content.addView(status)
        setContentView(ScrollView(this).apply { addView(content) })
    }

    private fun saveProfile() {
        profileStore.save(CustomKeyboardProfile(language.text.toString().trim(), name.text.toString().trim(), rows.text.toString().split("|").map(String::trim).filter(String::isNotBlank)))
        if (apiKey.text.isNotBlank()) keyStore.save(apiKey.text.toString().trim())
        status.text = "Saved locally. Reopen the keyboard to apply it."
    }

    private fun generate() {
        val supplied = apiKey.text.toString().trim().ifBlank { keyStore.load().orEmpty() }
        status.text = "Generating locally through your configured API…"
        AiKeyboardDesigner().generate(supplied, language.text.toString().trim(), prompt.text.toString(), { result ->
            runOnUiThread {
                result.onSuccess {
                    name.setText(it.displayName); rows.setText(it.rows.joinToString("|")); profileStore.save(it)
                    status.text = "AI profile saved locally. Reopen the keyboard to apply it."
                }.onFailure { status.text = it.message ?: "AI request failed" }
            }
        })
    }

    private fun field(hint: String, value: String) = EditText(this).apply {
        this.hint = hint; setText(value); setPadding(0, 14, 0, 14)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
