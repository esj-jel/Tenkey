package dev.localkeyboard.ui

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TutorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val page = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(28, 28, 28, 28) }
        page.addView(TextView(this).apply { text = "Kotoba quick tour"; textSize = 30f })
        page.addView(section("1  Learn", "Open Kotoba Grammar's daily lesson to study a character or word with a translation, explanation and pronunciation guide."))
        page.addView(section("2  Choose a language", "The catalog contains 111 labels. Offline starter lessons cover Japanese, Cantonese, Korean and Simplified and Traditional Chinese. Other choices currently fall back to Japanese content."))
        page.addView(section("3  Type", "Enable Kotoba Grammar under Android Settings → System → Keyboard, then select it. Tap カナ to switch Japanese syllabary."))
        page.addView(section("4  Move the cursor", "Hold and drag across the space key to move the cursor through the current text."))
        page.addView(section("5  Keep it local", "Lessons, preferences, custom keyboard layouts and imported fonts stay on this device. The app has no network permission."))
        setContentView(ScrollView(this).apply { addView(page) })
    }

    private fun section(title: String, body: String) = TextView(this).apply {
        text = "$title\n$body"; textSize = 17f; gravity = Gravity.START
        setPadding(0, 20, 0, 10)
    }
}
