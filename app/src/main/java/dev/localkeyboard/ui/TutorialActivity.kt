package dev.localkeyboard.ui

import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val page = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(28, 28, 28, 28) }
        page.addView(TextView(this).apply { text = "Tenkey quick tour"; textSize = 30f; setTextColor(Color.rgb(45, 38, 55)) })
        page.addView(section("1  Type", "The default layout is Japanese Hiragana tenkey. Tap カナ to switch to Katakana."))
        page.addView(section("2  Switch languages", "Tap the globe to move to the next Android keyboard. Hold the globe to open Tenkey’s language shelf and choose one of your selected languages."))
        page.addView(section("3  Move the cursor", "Hold and drag across the space key to move the cursor precisely through the current text."))
        page.addView(section("4  Open tools", "Use the face key for emoji. The utility panels are designed for kaomojis, clipboard history, and developer symbols."))
        page.addView(section("5  Make it yours", "Open Custom keyboard studio to choose a language, edit rows, pick colors, import a font, or ask the optional AI designer for a layout."))
        page.addView(section("6  Privacy", "Offline keyboard functions stay on-device. The optional AI designer only runs after you enter your own API key; the key is encrypted with Android Keystore."))
        setContentView(ScrollView(this).apply { addView(page) })
    }

    private fun section(title: String, body: String) = TextView(this).apply {
        text = "$title\n$body"; textSize = 17f; setTextColor(Color.DKGRAY); gravity = Gravity.START
        setPadding(0, 20, 0, 10)
    }
}
