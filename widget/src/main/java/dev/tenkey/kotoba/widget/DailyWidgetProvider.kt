package dev.tenkey.kotoba.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.time.LocalDate

internal data class WidgetCard(
    val written: String,
    val translation: String,
    val explanation: String,
    val reading: String,
    val isCharacter: Boolean
)

/** Bundled offline starter packs. Other catalog languages use the Japanese starter pack. */
internal object WidgetLessons {
    private val lessons = mapOf(
        "ja" to listOf(
            WidgetCard("日", "sun · day", "Kanji for sun or day; 日 is also its own radical.", "nichi · hi", true),
            WidgetCard("月", "moon · month", "Kanji for moon or month; 月 is also a common radical.", "tsuki · getsu", true),
            WidgetCard("氵", "water radical", "The three-dot form of 水 appears in many water-related kanji.", "sanzui", true),
            WidgetCard("水", "water", "The kanji for water; its left-side variant is 氵.", "mizu", true),
            WidgetCard("猫", "cat", "A common noun; its left-side radical 犭 is linked to animals.", "neko", false),
            WidgetCard("ありがとう", "thank you", "A friendly everyday expression of gratitude.", "arigatō", false),
            WidgetCard("学校", "school", "学 (study) + 校 (school/building).", "gakkō", false),
            WidgetCard("食べる", "to eat", "A common ru-verb; る changes during conjugation.", "taberu", false),
            WidgetCard("友だち", "friend", "An everyday word for a friend or friends.", "tomodachi", false)
        ),
        "yue" to listOf(
            WidgetCard("我", "I · me", "A common first-person pronoun in Cantonese.", "ngo5 · Jyutping", true),
            WidgetCard("你", "you", "A common second-person pronoun.", "nei5 · Jyutping", true),
            WidgetCard("水", "water", "A shared written character with a Cantonese reading.", "seoi2 · Jyutping", true),
            WidgetCard("你好", "hello", "A common greeting; literally you + good.", "nei5 hou2 · Jyutping", false),
            WidgetCard("多謝", "thank you", "Used especially for a gift or favour.", "do1 ze6 · Jyutping", false),
            WidgetCard("早晨", "good morning", "A common morning greeting.", "zou2 san4 · Jyutping", false),
            WidgetCard("朋友", "friend", "A common word for a friend.", "pang4 jau5 · Jyutping", false)
        ),
        "ko" to listOf(
            WidgetCard("ㄱ", "g · k", "Hangul consonant whose sound depends on position and context.", "giyeok", true),
            WidgetCard("ㅏ", "a", "Hangul vowel with a sound similar to the a in father.", "a", true),
            WidgetCard("한", "one · Korean", "A Hangul syllable block built from ㅎ + ㅏ + ㄴ.", "han", true),
            WidgetCard("안녕", "hello · peace", "Informal greeting; 안녕하세요 is polite.", "annyeong", false),
            WidgetCard("감사합니다", "thank you", "A polite, commonly used expression of thanks.", "gamsahamnida", false),
            WidgetCard("물", "water", "Everyday native Korean word for water.", "mul", false),
            WidgetCard("친구", "friend", "A common word for a friend.", "chingu", false)
        ),
        "zh-Hans" to listOf(
            WidgetCard("人", "person", "A pictographic character for a person; also a common component.", "rén · Pinyin", true),
            WidgetCard("口", "mouth", "Means mouth and is also a common radical.", "kǒu · Pinyin", true),
            WidgetCard("山", "mountain", "A character whose shape evokes mountain peaks.", "shān · Pinyin", true),
            WidgetCard("你好", "hello", "A standard greeting formed from you + good.", "nǐ hǎo · Pinyin", false),
            WidgetCard("谢谢", "thank you", "A common expression of gratitude.", "xièxie · Pinyin", false),
            WidgetCard("朋友", "friend", "A common word for a friend.", "péngyou · Pinyin", false),
            WidgetCard("学习", "to study", "A useful verb and noun about learning.", "xuéxí · Pinyin", false)
        ),
        "zh-Hant" to listOf(
            WidgetCard("人", "person", "A pictographic character for a person; also a common component.", "rén · Pinyin", true),
            WidgetCard("口", "mouth", "Means mouth and is also a common radical.", "kǒu · Pinyin", true),
            WidgetCard("山", "mountain", "A character whose shape evokes mountain peaks.", "shān · Pinyin", true),
            WidgetCard("你好", "hello", "A standard greeting formed from you + good.", "nǐ hǎo · Pinyin", false),
            WidgetCard("謝謝", "thank you", "A common expression of gratitude.", "xièxie · Pinyin", false),
            WidgetCard("朋友", "friend", "A common word for a friend.", "péngyou · Pinyin", false),
            WidgetCard("學習", "to study", "A useful verb and noun about learning.", "xuéxí · Pinyin", false)
        ),
        "en" to listOf(
            WidgetCard("A", "letter A", "First letter of the English alphabet; it has several sounds.", "ay", true),
            WidgetCard("B", "letter B", "A consonant usually voiced as in ball.", "bee", true),
            WidgetCard("C", "letter C", "Often sounds like k in cat or s in city.", "see", true),
            WidgetCard("hello", "hello; greeting", "A friendly greeting used when meeting someone.", "heh-LOH", false),
            WidgetCard("thank you", "thanks", "A polite expression of gratitude.", "THANK yoo", false)
        ),
        "es" to listOf(
            WidgetCard("Ñ", "eñe", "Spanish letter pronounced with a palatal ny sound.", "enye", true),
            WidgetCard("A", "letter A", "Spanish vowel with a clear open ah sound.", "a", true),
            WidgetCard("B", "letter B", "Often pronounced similarly to V in Spanish.", "be", true),
            WidgetCard("hola", "hello", "A common greeting; the h is silent.", "OH-la", false),
            WidgetCard("gracias", "thank you", "A common expression of gratitude.", "GRA-syas", false)
        ),
        "fr" to listOf(
            WidgetCard("É", "accented e", "E acute marks a close e sound and appears in many French words.", "ay", true),
            WidgetCard("A", "letter A", "French vowel whose sound differs from English A.", "ah", true),
            WidgetCard("B", "letter B", "French consonant with a voiced b sound.", "bay", true),
            WidgetCard("bonjour", "hello; good day", "A standard daytime greeting.", "bohn-ZHOOR", false),
            WidgetCard("merci", "thank you", "A common expression of thanks.", "mehr-SEE", false)
        ),
        "de" to listOf(
            WidgetCard("Ä", "A-umlaut", "A German vowel written with an umlaut; its sound varies by word.", "eh", true),
            WidgetCard("A", "letter A", "German A is generally an open ah sound.", "ah", true),
            WidgetCard("ß", "Eszett", "A letter called Eszett, usually pronounced like a sharp s.", "ess-tsett", true),
            WidgetCard("hallo", "hello", "An informal everyday greeting.", "HA-loh", false),
            WidgetCard("danke", "thank you", "A common way to say thanks.", "DAHN-kuh", false)
        ),
        "it" to listOf(
            WidgetCard("È", "E with grave accent", "The accented capital E is also the word meaning is.", "eh", true),
            WidgetCard("A", "letter A", "Italian A is a clear open ah vowel.", "ah", true),
            WidgetCard("B", "letter B", "Italian consonant pronounced with a voiced b.", "bee", true),
            WidgetCard("ciao", "hello; goodbye", "Informal greeting used when arriving or leaving.", "CHOW", false),
            WidgetCard("grazie", "thank you", "A common expression of gratitude.", "GRAHT-tsyeh", false)
        ),
        "pt" to listOf(
            WidgetCard("Ã", "A with tilde", "The tilde marks nasalization in Portuguese vowels.", "ã · nasal ah", true),
            WidgetCard("A", "letter A", "Portuguese A may be open or reduced depending on stress.", "ah", true),
            WidgetCard("Ç", "c-cedilla", "Before a, o, u it marks an s sound.", "seh", true),
            WidgetCard("olá", "hello", "A friendly greeting; stress falls on the final syllable.", "oh-LAH", false),
            WidgetCard("obrigado", "thank you", "Traditionally said by a male speaker; obrigada by a female speaker.", "oh-bree-GAH-doo", false)
        ),
        "hi" to listOf(
            WidgetCard("क", "ka", "Devanagari consonant क, commonly transliterated ka.", "ka", true),
            WidgetCard("म", "ma", "Devanagari consonant म, commonly transliterated ma.", "ma", true),
            WidgetCard("अ", "a", "Independent Devanagari vowel अ.", "a", true),
            WidgetCard("नमस्ते", "hello; greetings", "A respectful greeting used on meeting someone.", "namaste", false),
            WidgetCard("धन्यवाद", "thank you", "A formal expression of thanks.", "dhanyavaad", false)
        )
    )

    fun get(tag: String, index: Int): WidgetCard {
        val pack = lessons[tag] ?: lessons.getValue("ja")
        return pack[Math.floorMod(index, pack.size)]
    }

    fun daily(tag: String, date: LocalDate = LocalDate.now()): WidgetCard =
        get(tag, date.toEpochDay().toInt())
}

class DailyWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, manager: AppWidgetManager, ids: IntArray) =
        ids.forEach { update(context, manager, it) }

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
            if (prefs.getString("date", "") != today.toString()) {
                prefs.edit().putString("date", today.toString()).putInt("step", today.dayOfYear).apply()
            }
            val card = WidgetLessons.get(lang, prefs.getInt("step", today.dayOfYear))
            val views = RemoteViews(context.packageName, R.layout.daily_widget).apply {
                setTextViewText(R.id.widget_label, if (card.isCharacter) "KOTOBA · NEW CHARACTER" else "KOTOBA · NEW WORD")
                setTextViewText(R.id.widget_word, card.written)
                setTextViewText(R.id.widget_translation, card.translation)
                setTextViewText(R.id.widget_explanation, card.explanation)
                setTextViewText(R.id.widget_pronunciation, card.reading)
                val click = PendingIntent.getBroadcast(
                    context, 0,
                    Intent(context, DailyWidgetProvider::class.java).setAction(ACTION_NEXT),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                setOnClickPendingIntent(R.id.widget_word, click)
                setOnClickPendingIntent(R.id.widget_translation, click)
                setOnClickPendingIntent(R.id.widget_explanation, click)
                setOnClickPendingIntent(R.id.widget_pronunciation, click)
            }
            manager.updateAppWidget(id, views)
        }
    }
}
