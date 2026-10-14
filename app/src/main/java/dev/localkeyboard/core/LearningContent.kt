package dev.localkeyboard.core

import java.time.LocalDate

enum class LessonKind { CHARACTER, WORD }

data class StudyCard(
    val written: String,
    val translation: String,
    val explanation: String,
    val pronunciation: String,
    val kind: LessonKind
)

/** Small, license-safe starter packs shipped in the app; never makes a network request. */
object LearningContent {
    private val packs = mapOf(
        "ja" to listOf(
            StudyCard("日", "sun; day", "A kanji meaning sun or day. Radical: 日 (sun).", "にち · nichi", LessonKind.CHARACTER),
            StudyCard("月", "moon; month", "A kanji meaning moon or month. Radical: 月 (moon).", "つき · tsuki", LessonKind.CHARACTER),
            StudyCard("あ", "a", "First hiragana vowel; a foundational Japanese sound.", "a", LessonKind.CHARACTER),
            StudyCard("水", "water", "Kanji for water. The left-side radical 氵 often relates to liquids.", "みず · mizu", LessonKind.CHARACTER),
            StudyCard("ありがとう", "thank you", "A friendly everyday expression of gratitude.", "arigatō", LessonKind.WORD),
            StudyCard("おはよう", "good morning", "Casual morning greeting; add ございます for polite speech.", "ohayō", LessonKind.WORD),
            StudyCard("猫", "cat", "A common noun. Its radical 犭 is associated with animals.", "ねこ · neko", LessonKind.WORD),
            StudyCard("学校", "school", "Made from 学 (study) and 校 (school/building).", "がっこう · gakkō", LessonKind.WORD),
            StudyCard("食べる", "to eat", "A ru-verb. The る ending changes when conjugated.", "たべる · taberu", LessonKind.WORD),
            StudyCard("友だち", "friend", "An everyday word for a friend or friends.", "ともだち · tomodachi", LessonKind.WORD)
        ),
        "yue" to listOf(
            StudyCard("我", "I; me", "A common first-person pronoun in Cantonese.", "ngo5 (Jyutping)", LessonKind.CHARACTER),
            StudyCard("你", "you", "A common second-person pronoun.", "nei5 (Jyutping)", LessonKind.CHARACTER),
            StudyCard("水", "water", "The character is shared with written Chinese; Cantonese has its own reading.", "seoi2 (Jyutping)", LessonKind.CHARACTER),
            StudyCard("你好", "hello", "A standard greeting; literally combines you + good.", "nei5 hou2 (Jyutping)", LessonKind.WORD),
            StudyCard("多謝", "thank you", "Used especially when thanking someone for a gift or favour.", "do1 ze6 (Jyutping)", LessonKind.WORD),
            StudyCard("早晨", "good morning", "A common morning greeting.", "zou2 san4 (Jyutping)", LessonKind.WORD),
            StudyCard("食飯", "eat a meal", "Literally eat + rice/meal; often used for having a meal.", "sik6 faan6 (Jyutping)", LessonKind.WORD),
            StudyCard("朋友", "friend", "A common word for a friend.", "pang4 jau5 (Jyutping)", LessonKind.WORD)
        ),
        "ko" to listOf(
            StudyCard("ㄱ", "g/k", "Hangul consonant; its sound shifts with position and context.", "giyeok", LessonKind.CHARACTER),
            StudyCard("ㅏ", "a", "Hangul vowel pronounced like the a in father.", "a", LessonKind.CHARACTER),
            StudyCard("한", "one; Korean", "A Hangul syllable block built from ㅎ + ㅏ + ㄴ.", "han", LessonKind.CHARACTER),
            StudyCard("안녕", "hello; peace", "Informal greeting; use 안녕하세요 to be polite.", "annyeong", LessonKind.WORD),
            StudyCard("감사합니다", "thank you", "Polite, commonly used expression of thanks.", "gamsahamnida", LessonKind.WORD),
            StudyCard("물", "water", "Everyday native Korean word for water.", "mul", LessonKind.WORD),
            StudyCard("친구", "friend", "Common word for a friend.", "chingu", LessonKind.WORD),
            StudyCard("학교", "school", "A Sino-Korean word; compare 학 (learning) + 교 (school).", "hakgyo", LessonKind.WORD)
        ),
        "zh-Hans" to listOf(
            StudyCard("人", "person", "A pictographic character for a person; also a frequent component.", "rén (Pinyin)", LessonKind.CHARACTER),
            StudyCard("口", "mouth", "A character meaning mouth; also a common radical.", "kǒu (Pinyin)", LessonKind.CHARACTER),
            StudyCard("山", "mountain", "A character whose shape evokes mountain peaks.", "shān (Pinyin)", LessonKind.CHARACTER),
            StudyCard("你好", "hello", "A standard greeting formed from you + good.", "nǐ hǎo (Pinyin)", LessonKind.WORD),
            StudyCard("谢谢", "thank you", "A common expression of gratitude.", "xièxie (Pinyin)", LessonKind.WORD),
            StudyCard("朋友", "friend", "A common word for a friend.", "péngyou (Pinyin)", LessonKind.WORD),
            StudyCard("早上好", "good morning", "A morning greeting: morning + good.", "zǎoshang hǎo (Pinyin)", LessonKind.WORD),
            StudyCard("学习", "to study; learning", "A useful verb and noun about learning.", "xuéxí (Pinyin)", LessonKind.WORD)
        ),
        "zh-Hant" to listOf(
            StudyCard("人", "person", "A pictographic character for a person; also a frequent component.", "rén (Pinyin)", LessonKind.CHARACTER),
            StudyCard("口", "mouth", "A character meaning mouth; also a common radical.", "kǒu (Pinyin)", LessonKind.CHARACTER),
            StudyCard("山", "mountain", "A character whose shape evokes mountain peaks.", "shān (Pinyin)", LessonKind.CHARACTER),
            StudyCard("你好", "hello", "A standard greeting formed from you + good.", "nǐ hǎo (Pinyin)", LessonKind.WORD),
            StudyCard("謝謝", "thank you", "A common expression of gratitude.", "xièxie (Pinyin)", LessonKind.WORD),
            StudyCard("朋友", "friend", "A common word for a friend.", "péngyou (Pinyin)", LessonKind.WORD),
            StudyCard("早上好", "good morning", "A morning greeting: morning + good.", "zǎoshang hǎo (Pinyin)", LessonKind.WORD),
            StudyCard("學習", "to study; learning", "A useful verb and noun about learning.", "xuéxí (Pinyin)", LessonKind.WORD)
        ),
        "en" to listOf(
            StudyCard("A", "letter A", "First letter of the English alphabet; it has several sounds.", "ay", LessonKind.CHARACTER),
            StudyCard("B", "letter B", "A consonant usually voiced as in ball.", "bee", LessonKind.CHARACTER),
            StudyCard("C", "letter C", "Often sounds like k in cat or s in city.", "see", LessonKind.CHARACTER),
            StudyCard("hello", "hello; greeting", "A friendly greeting used when meeting someone.", "heh-LOH", LessonKind.WORD),
            StudyCard("thank you", "thanks", "A polite expression of gratitude.", "THANK yoo", LessonKind.WORD),
            StudyCard("friend", "friend", "A person you know and like.", "FREND", LessonKind.WORD)
        ),
        "es" to listOf(
            StudyCard("Ñ", "eñe", "Spanish letter pronounced with a palatal ny sound.", "enye", LessonKind.CHARACTER),
            StudyCard("A", "letter A", "Spanish vowel with a clear open ah sound.", "a", LessonKind.CHARACTER),
            StudyCard("B", "letter B", "Often pronounced similarly to V in Spanish.", "be", LessonKind.CHARACTER),
            StudyCard("hola", "hello", "A common greeting; the h is silent.", "OH-la", LessonKind.WORD),
            StudyCard("gracias", "thank you", "A common expression of gratitude.", "GRA-syas", LessonKind.WORD),
            StudyCard("amigo", "friend", "A male friend; amiga is feminine.", "a-MEE-go", LessonKind.WORD)
        ),
        "fr" to listOf(
            StudyCard("É", "accented e", "E acute marks a close e sound and appears in many French words.", "ay", LessonKind.CHARACTER),
            StudyCard("A", "letter A", "French vowel whose sound differs from English A.", "ah", LessonKind.CHARACTER),
            StudyCard("B", "letter B", "French consonant with a voiced b sound.", "bay", LessonKind.CHARACTER),
            StudyCard("bonjour", "hello; good day", "A standard daytime greeting.", "bohn-ZHOOR", LessonKind.WORD),
            StudyCard("merci", "thank you", "A common expression of thanks.", "mehr-SEE", LessonKind.WORD),
            StudyCard("ami", "friend", "A male friend; amie is feminine.", "ah-MEE", LessonKind.WORD)
        ),
        "de" to listOf(
            StudyCard("Ä", "A-umlaut", "A German vowel written with an umlaut; its sound varies by word.", "eh", LessonKind.CHARACTER),
            StudyCard("A", "letter A", "German A is generally an open ah sound.", "ah", LessonKind.CHARACTER),
            StudyCard("ß", "Eszett", "A letter called Eszett, usually pronounced like a sharp s.", "ess-tsett", LessonKind.CHARACTER),
            StudyCard("hallo", "hello", "An informal everyday greeting.", "HA-loh", LessonKind.WORD),
            StudyCard("danke", "thank you", "A common way to say thanks.", "DAHN-kuh", LessonKind.WORD),
            StudyCard("Freund", "friend", "A male friend; Freundin is feminine.", "froynt", LessonKind.WORD)
        ),
        "it" to listOf(
            StudyCard("È", "E with grave accent", "The accented capital E is also the word meaning is.", "eh", LessonKind.CHARACTER),
            StudyCard("A", "letter A", "Italian A is a clear open ah vowel.", "ah", LessonKind.CHARACTER),
            StudyCard("B", "letter B", "Italian consonant pronounced with a voiced b.", "bee", LessonKind.CHARACTER),
            StudyCard("ciao", "hello; goodbye", "Informal greeting used when arriving or leaving.", "CHOW", LessonKind.WORD),
            StudyCard("grazie", "thank you", "A common expression of gratitude.", "GRAHT-tsyeh", LessonKind.WORD),
            StudyCard("amico", "friend", "A male friend; amica is feminine.", "ah-MEE-koh", LessonKind.WORD)
        ),
        "pt" to listOf(
            StudyCard("Ã", "A with tilde", "The tilde marks nasalization in Portuguese vowels.", "ã · nasal ah", LessonKind.CHARACTER),
            StudyCard("A", "letter A", "Portuguese A may be open or reduced depending on stress.", "ah", LessonKind.CHARACTER),
            StudyCard("Ç", "c-cedilla", "Before a, o, u it marks an s sound.", "seh", LessonKind.CHARACTER),
            StudyCard("olá", "hello", "A friendly greeting; stress falls on the final syllable.", "oh-LAH", LessonKind.WORD),
            StudyCard("obrigado", "thank you", "Traditionally said by a male speaker; obrigada by a female speaker.", "oh-bree-GAH-doo", LessonKind.WORD),
            StudyCard("amigo", "friend", "A male friend; amiga is feminine.", "ah-MEE-goo", LessonKind.WORD)
        ),
        "hi" to listOf(
            StudyCard("क", "ka", "Devanagari consonant क, commonly transliterated ka.", "ka", LessonKind.CHARACTER),
            StudyCard("म", "ma", "Devanagari consonant म, commonly transliterated ma.", "ma", LessonKind.CHARACTER),
            StudyCard("अ", "a", "Independent Devanagari vowel अ.", "a", LessonKind.CHARACTER),
            StudyCard("नमस्ते", "hello; greetings", "A respectful greeting used on meeting someone.", "namaste", LessonKind.WORD),
            StudyCard("धन्यवाद", "thank you", "A formal expression of thanks.", "dhanyavaad", LessonKind.WORD),
            StudyCard("पानी", "water", "The everyday Hindi word for water.", "paani", LessonKind.WORD)
        )
    )

    fun cards(languageTag: String): List<StudyCard> = packs[languageTag] ?: packs.getValue("ja")
    fun supportedLessonTags(): Set<String> = packs.keys

    /** Stable daily index for widgets. The lesson order begins with characters, then words. */
    fun cardForDay(languageTag: String, date: LocalDate = LocalDate.now()): StudyCard {
        val cards = cards(languageTag)
        val index = (date.toEpochDay().mod(cards.size.toLong())).toInt()
        return cards[index]
    }

    fun next(languageTag: String, step: Int): StudyCard {
        val cards = cards(languageTag)
        return cards[Math.floorMod(step, cards.size)]
    }
}
