package dev.tenkey.kotoba.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.color.DynamicColors
import org.json.JSONArray

data class WidgetLanguage(val tag: String, val displayName: String)

class WidgetSettingsActivity : Activity() {
    private val languages: List<WidgetLanguage> by lazy {
        runCatching {
            val json = JSONArray(assets.open("languages.json").bufferedReader().use { it.readText() })
            (0 until json.length()).map { index ->
                val row = json.getJSONObject(index)
                WidgetLanguage(row.getString("tag"), row.getString("displayName"))
            }
        }.getOrElse {
            listOf(WidgetLanguage("ja", "日本語"), WidgetLanguage("yue", "粵語"), WidgetLanguage("ko", "한국어"), WidgetLanguage("zh-Hans", "简体中文"), WidgetLanguage("zh-Hant", "繁體中文"))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 32, 32, 32) }
        root.addView(TextView(this).apply { text = "Kotoba Widget"; textSize = 28f })
        root.addView(TextView(this).apply { text = "Choose from 111 language labels, then add the transparent study widget from your home-screen widget gallery. 12 offline starter packs are included; the remaining 99 languages use the Japanese fallback pack."; textSize = 15f; setPadding(0, 12, 0, 18) })
        val spinner = Spinner(this)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages.map { "${it.displayName} · ${it.tag}" })
        val prefs = getSharedPreferences(DailyWidgetProvider.PREFS, MODE_PRIVATE)
        val old = prefs.getString("language", "ja") ?: "ja"
        spinner.setSelection(languages.indexOfFirst { it.tag == old }.coerceAtLeast(0))
        root.addView(spinner)
        root.addView(Button(this).apply {
            text = "Save language"
            setOnClickListener {
                val selected = languages[spinner.selectedItemPosition]
                val today = java.time.LocalDate.now()
                prefs.edit().putString("language", selected.tag).putInt("step", today.dayOfYear).putString("date", today.toString()).apply()
                DailyWidgetProvider.refreshAll(this@WidgetSettingsActivity)
                val result = android.content.Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, intent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID) ?: AppWidgetManager.INVALID_APPWIDGET_ID)
                setResult(RESULT_OK, result)
                finish()
            }
        })
        setContentView(root)
    }
}
