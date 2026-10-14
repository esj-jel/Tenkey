package dev.tenkey.kotoba.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.color.DynamicColors

class WidgetSettingsActivity : Activity() {
    private val languages = listOf("Japanese" to "ja", "Cantonese" to "yue", "Korean" to "ko", "Chinese (Simplified)" to "zh-Hans", "Chinese (Traditional)" to "zh-Hant")
    override fun onCreate(savedInstanceState: Bundle?) {
        DynamicColors.applyToActivityIfAvailable(this)
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(32, 32, 32, 32) }
        root.addView(TextView(this).apply { text = "Kotoba Widget"; textSize = 28f })
        root.addView(TextView(this).apply { text = "Choose a local study pack, then add the transparent widget from your home-screen widget gallery. If your Android version offers lock-screen widgets, the same widget can be pinned there."; textSize = 15f; setPadding(0, 12, 0, 18) })
        val spinner = Spinner(this)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, languages.map { it.first })
        val prefs = getSharedPreferences(DailyWidgetProvider.PREFS, MODE_PRIVATE)
        val old = prefs.getString("language", "ja") ?: "ja"
        spinner.setSelection(languages.indexOfFirst { it.second == old }.coerceAtLeast(0))
        root.addView(spinner)
        root.addView(Button(this).apply {
            text = "Save language"
            setOnClickListener {
                prefs.edit().putString("language", languages[spinner.selectedItemPosition].second).putInt("step", java.time.LocalDate.now().dayOfYear).putString("date", java.time.LocalDate.now().toString()).apply()
                DailyWidgetProvider.refreshAll(this@WidgetSettingsActivity)
                setResult(RESULT_OK, intent?.extras?.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)?.let { id ->
                    android.content.Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
                } ?: android.content.Intent())
                finish()
            }
        })
        setContentView(root)
    }
}
