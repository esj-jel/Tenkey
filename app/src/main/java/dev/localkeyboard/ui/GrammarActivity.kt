package dev.localkeyboard.ui

import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors
import dev.localkeyboard.core.LanguageCatalog
import dev.localkeyboard.core.LearningContent
import dev.localkeyboard.core.StudyCard

class GrammarActivity : AppCompatActivity() {
    private lateinit var picker: Spinner
    private lateinit var cardView: LinearLayout
    private lateinit var countLabel: TextView
    private var step = 0
    private val prefs by lazy { getSharedPreferences("kotoba_grammar", MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        step = prefs.getInt("step", 0)
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(18), dp(24), dp(28))
            setBackgroundColor(Color.rgb(250, 248, 253))
        }
        page.addView(label("Kotoba Grammar", 30f, true, Color.rgb(48, 39, 61)))
        page.addView(label("A small daily lesson, stored and studied on this device.", 15f, false, Color.rgb(92, 85, 101)))
        page.addView(label("LEARN A LANGUAGE", 12f, true, Color.rgb(90, 70, 126)).apply { setPadding(0, dp(24), 0, dp(6)) })
        picker = Spinner(this)
        picker.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, LanguageCatalog.all.map { "${it.displayName}  ·  ${it.tag}" })
        val preferred = prefs.getString("language", "ja") ?: "ja"
        picker.setSelection(LanguageCatalog.all.indexOfFirst { it.tag == preferred }.coerceAtLeast(0))
        page.addView(picker)
        cardView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(22), dp(22), dp(24))
            background = rounded(0xFFEDE7F5.toInt(), dp(26).toFloat())
        }
        val cardWrap = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(0, dp(22), 0, 0) }
        cardWrap.addView(cardView)
        page.addView(cardWrap)
        countLabel = label("", 13f, false, Color.rgb(95, 88, 103)).apply { setPadding(0, dp(16), 0, 0) }
        page.addView(countLabel)
        page.addView(Button(this).apply {
            text = "Next character / word"
            setOnClickListener {
                step++
                prefs.edit().putInt("step", step).putString("language", selectedTag()).apply()
                renderCard()
            }
        }.apply { setPadding(0, dp(8), 0, dp(8)) })
        page.addView(label("Offline pack note: the catalog contains 111 language labels. Starter lessons are currently bundled for Japanese, Cantonese, Korean and Chinese; other selections use the Japanese starter pack until a local pack is added.", 13f, false, Color.rgb(101, 94, 109)).apply { setPadding(0, dp(22), 0, 0) })
        picker.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) = Unit
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: View?, position: Int, id: Long) {
                prefs.edit().putString("language", LanguageCatalog.all[position].tag).apply()
                renderCard()
            }
        }
        setContentView(ScrollView(this).apply { addView(page) })
    }

    override fun onResume() {
        super.onResume()
        if (::cardView.isInitialized && ::picker.isInitialized) {
            step = prefs.getInt("step", step)
            renderCard()
        }
    }

    private fun selectedTag() = LanguageCatalog.all.getOrNull(picker.selectedItemPosition)?.tag ?: "ja"

    private fun renderCard() {
        if (!::cardView.isInitialized || !::picker.isInitialized) return
        val tag = selectedTag()
        val lesson = LearningContent.next(tag, step)
        cardView.removeAllViews()
        cardView.addView(label(if (lesson.kind.name == "CHARACTER") "NEW CHARACTER" else "NEW WORD", 12f, true, Color.rgb(103, 80, 164)))
        cardView.addView(label(lesson.written, if (lesson.written.length <= 2) 58f else 43f, true, Color.rgb(37, 32, 44)).apply { setPadding(0, dp(10), 0, dp(8)) })
        cardView.addView(label(lesson.translation, 22f, true, Color.rgb(58, 49, 72)))
        cardView.addView(label(lesson.explanation, 15f, false, Color.rgb(75, 68, 83)).apply { setPadding(0, dp(14), 0, dp(14)) })
        cardView.addView(label("PRONUNCIATION", 11f, true, Color.rgb(103, 80, 164)))
        cardView.addView(label(lesson.pronunciation, 18f, true, Color.rgb(48, 39, 61)).apply { setPadding(0, dp(4), 0, 0) })
        countLabel.text = "Lesson ${step + 1}  ·  ${LanguageCatalog.all.firstOrNull { it.tag == tag }?.displayName ?: "Japanese"}"
    }

    private fun label(textValue: String, size: Float, bold: Boolean, color: Int) = TextView(this).apply {
        text = textValue; textSize = size; setTextColor(color)
        if (bold) setTypeface(typeface, Typeface.BOLD)
        gravity = Gravity.START
    }
    private fun rounded(color: Int, radius: Float) = GradientDrawable().apply { setColor(color); cornerRadius = radius }
    private fun dp(value: Int) = (value * resources.displayMetrics.density).toInt()
}
