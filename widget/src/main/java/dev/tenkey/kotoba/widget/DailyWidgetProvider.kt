package dev.tenkey.kotoba.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.time.LocalDate

internal data class WidgetCard(val written: String, val translation: String, val reading: String)

internal object WidgetLessons {
    private val lessons = mapOf(
        "ja" to listOf(WidgetCard("日", "sun · day", "nichi"), WidgetCard("月", "moon · month", "tsuki"), WidgetCard("猫", "cat", "neko"), WidgetCard("ありがとう", "thank you", "arigatō")),
        "yue" to listOf(WidgetCard("我", "I · me", "ngo5"), WidgetCard("你好", "hello", "nei5 hou2"), WidgetCard("多謝", "thank you", "do1 ze6"), WidgetCard("朋友", "friend", "pang4 jau5")),
        "ko" to listOf(WidgetCard("ㄱ", "Hangul consonant", "giyeok"), WidgetCard("안녕", "hello", "annyeong"), WidgetCard("물", "water", "mul"), WidgetCard("친구", "friend", "chingu")),
        "zh" to listOf(WidgetCard("人", "person", "rén"), WidgetCard("你好", "hello", "nǐ hǎo"), WidgetCard("谢谢", "thank you", "xièxie"), WidgetCard("朋友", "friend", "péngyou"))
    )
    fun get(tag: String, index: Int): WidgetCard {
        val key = if (tag == "yue" || tag == "ko") tag else if (tag.startsWith("zh")) "zh" else "ja"
        val pack = lessons.getValue(key)
        return pack[Math.floorMod(index, pack.size)]
    }
    fun daily(tag: String, date: LocalDate = LocalDate.now()): WidgetCard = get(tag, date.toEpochDay().toInt())
}

class DailyWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) = ids.forEach { update(context, manager, it) }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == ACTION_NEXT) {
            val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            prefs.edit().putInt("step", prefs.getInt("step", 0) + 1).apply()
            refreshAll(context)
        }
    }

    companion object {
        const val ACTION_NEXT = "dev.tenkey.kotoba.widget.NEXT_LESSON"
        const val PREFS = "kotoba_widget"
        fun refreshAll(context: Context) {
            val manager = AppWidgetManager.getInstance(context)
            val ids = manager.getAppWidgetIds(ComponentName(context, DailyWidgetProvider::class.java))
            ids.forEach { update(context, manager, it) }
        }
        private fun update(context: Context, manager: AppWidgetManager, id: Int) {
            val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            val lang = prefs.getString("language", "ja") ?: "ja"
            val today = LocalDate.now()
            val todayKey = today.toString()
            if (prefs.getString("date", "") != todayKey) {
                prefs.edit().putString("date", todayKey).putInt("step", today.dayOfYear).apply()
            }
            val card = WidgetLessons.get(lang, prefs.getInt("step", today.dayOfYear))
            val views = RemoteViews(context.packageName, R.layout.daily_widget).apply {
                setTextViewText(R.id.widget_word, card.written)
                setTextViewText(R.id.widget_translation, card.translation)
                setTextViewText(R.id.widget_pronunciation, card.reading)
                val click = PendingIntent.getBroadcast(context, 0, Intent(context, DailyWidgetProvider::class.java).setAction(ACTION_NEXT), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
                setOnClickPendingIntent(R.id.widget_word, click)
                setOnClickPendingIntent(R.id.widget_translation, click)
                setOnClickPendingIntent(R.id.widget_pronunciation, click)
            }
            manager.updateAppWidget(id, views)
        }
    }
}
