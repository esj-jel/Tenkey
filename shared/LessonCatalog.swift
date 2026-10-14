import Foundation

struct LessonCard {
    let written: String
    let translation: String
    let explanation: String
    let pronunciation: String
    let kind: String
}

enum LessonCatalog {
    static let lessons: [String: [LessonCard]] = [
        "ja": [
            .init(written: "日", translation: "sun · day", explanation: "Kanji meaning sun or day. Radical: 日 (sun).", pronunciation: "にち · nichi", kind: "New character"),
            .init(written: "月", translation: "moon · month", explanation: "Kanji meaning moon or month. Radical: 月 (moon).", pronunciation: "つき · tsuki", kind: "New character"),
            .init(written: "あ", translation: "a", explanation: "First hiragana vowel; a foundational Japanese sound.", pronunciation: "a", kind: "New character"),
            .init(written: "氵", translation: "water radical", explanation: "The three-dot form of 水 appears in many water-related kanji.", pronunciation: "sanzui", kind: "New character"),
            .init(written: "ありがとう", translation: "thank you", explanation: "A friendly, everyday expression of gratitude.", pronunciation: "arigatō", kind: "New word"),
            .init(written: "猫", translation: "cat", explanation: "A common noun; its animal radical is 犭.", pronunciation: "ねこ · neko", kind: "New word"),
            .init(written: "学校", translation: "school", explanation: "Built from 学 (study) and 校 (school/building).", pronunciation: "がっこう · gakkō", kind: "New word")
        ],
        "yue": [
            .init(written: "我", translation: "I · me", explanation: "A common first-person pronoun in Cantonese.", pronunciation: "ngo5 · Jyutping", kind: "New character"),
            .init(written: "你", translation: "you", explanation: "A common second-person pronoun.", pronunciation: "nei5 · Jyutping", kind: "New character"),
            .init(written: "你好", translation: "hello", explanation: "A common greeting; literally you + good.", pronunciation: "nei5 hou2 · Jyutping", kind: "New word"),
            .init(written: "多謝", translation: "thank you", explanation: "Used when thanking someone for a gift or favour.", pronunciation: "do1 ze6 · Jyutping", kind: "New word")
        ],
        "ko": [
            .init(written: "ㄱ", translation: "g · k", explanation: "Hangul consonant; sound varies by position and context.", pronunciation: "giyeok", kind: "New character"),
            .init(written: "ㅏ", translation: "a", explanation: "Hangul vowel, similar to the a in father.", pronunciation: "a", kind: "New character"),
            .init(written: "안녕", translation: "hello · peace", explanation: "Informal greeting; 안녕하세요 is polite.", pronunciation: "annyeong", kind: "New word"),
            .init(written: "감사합니다", translation: "thank you", explanation: "A polite, commonly used expression of thanks.", pronunciation: "gamsahamnida", kind: "New word")
        ],
        "zh-Hans": [
            .init(written: "人", translation: "person", explanation: "Pictographic character for a person; also a common component.", pronunciation: "rén · Pinyin", kind: "New character"),
            .init(written: "口", translation: "mouth", explanation: "Means mouth and is also a common radical.", pronunciation: "kǒu · Pinyin", kind: "New character"),
            .init(written: "你好", translation: "hello", explanation: "A standard greeting formed from you + good.", pronunciation: "nǐ hǎo · Pinyin", kind: "New word"),
            .init(written: "谢谢", translation: "thank you", explanation: "A common expression of gratitude.", pronunciation: "xièxie · Pinyin", kind: "New word")
        ],
        "zh-Hant": [
            .init(written: "人", translation: "person", explanation: "Pictographic character for a person; also a common component.", pronunciation: "rén · Pinyin", kind: "New character"),
            .init(written: "口", translation: "mouth", explanation: "Means mouth and is also a common radical.", pronunciation: "kǒu · Pinyin", kind: "New character"),
            .init(written: "你好", translation: "hello", explanation: "A standard greeting formed from you + good.", pronunciation: "nǐ hǎo · Pinyin", kind: "New word"),
            .init(written: "謝謝", translation: "thank you", explanation: "A common expression of gratitude.", pronunciation: "xièxie · Pinyin", kind: "New word")
        ],
        "en": [
            .init(written: "A", translation: "letter A", explanation: "First letter of the English alphabet; it has several sounds.", pronunciation: "ay", kind: "New character"),
            .init(written: "B", translation: "letter B", explanation: "A consonant usually voiced as in ball.", pronunciation: "bee", kind: "New character"),
            .init(written: "C", translation: "letter C", explanation: "Often sounds like k in cat or s in city.", pronunciation: "see", kind: "New character"),
            .init(written: "hello", translation: "hello; greeting", explanation: "A friendly greeting used when meeting someone.", pronunciation: "heh-LOH", kind: "New word"),
            .init(written: "thank you", translation: "thanks", explanation: "A polite expression of gratitude.", pronunciation: "THANK yoo", kind: "New word")
        ],
        "es": [
            .init(written: "Ñ", translation: "eñe", explanation: "Spanish letter pronounced with a palatal ny sound.", pronunciation: "enye", kind: "New character"),
            .init(written: "A", translation: "letter A", explanation: "Spanish vowel with a clear open ah sound.", pronunciation: "a", kind: "New character"),
            .init(written: "B", translation: "letter B", explanation: "Often pronounced similarly to V in Spanish.", pronunciation: "be", kind: "New character"),
            .init(written: "hola", translation: "hello", explanation: "A common greeting; the h is silent.", pronunciation: "OH-la", kind: "New word"),
            .init(written: "gracias", translation: "thank you", explanation: "A common expression of gratitude.", pronunciation: "GRA-syas", kind: "New word")
        ],
        "fr": [
            .init(written: "É", translation: "accented e", explanation: "E acute marks a close e sound and appears in many French words.", pronunciation: "ay", kind: "New character"),
            .init(written: "A", translation: "letter A", explanation: "French vowel whose sound differs from English A.", pronunciation: "ah", kind: "New character"),
            .init(written: "B", translation: "letter B", explanation: "French consonant with a voiced b sound.", pronunciation: "bay", kind: "New character"),
            .init(written: "bonjour", translation: "hello; good day", explanation: "A standard daytime greeting.", pronunciation: "bohn-ZHOOR", kind: "New word"),
            .init(written: "merci", translation: "thank you", explanation: "A common expression of thanks.", pronunciation: "mehr-SEE", kind: "New word")
        ],
        "de": [
            .init(written: "Ä", translation: "A-umlaut", explanation: "A German vowel written with an umlaut; its sound varies by word.", pronunciation: "eh", kind: "New character"),
            .init(written: "A", translation: "letter A", explanation: "German A is generally an open ah sound.", pronunciation: "ah", kind: "New character"),
            .init(written: "ß", translation: "Eszett", explanation: "A letter called Eszett, usually pronounced like a sharp s.", pronunciation: "ess-tsett", kind: "New character"),
            .init(written: "hallo", translation: "hello", explanation: "An informal everyday greeting.", pronunciation: "HA-loh", kind: "New word"),
            .init(written: "danke", translation: "thank you", explanation: "A common way to say thanks.", pronunciation: "DAHN-kuh", kind: "New word")
        ],
        "it": [
            .init(written: "È", translation: "E with grave accent", explanation: "The accented capital E is also the word meaning is.", pronunciation: "eh", kind: "New character"),
            .init(written: "A", translation: "letter A", explanation: "Italian A is a clear open ah vowel.", pronunciation: "ah", kind: "New character"),
            .init(written: "B", translation: "letter B", explanation: "Italian consonant pronounced with a voiced b.", pronunciation: "bee", kind: "New character"),
            .init(written: "ciao", translation: "hello; goodbye", explanation: "Informal greeting used when arriving or leaving.", pronunciation: "CHOW", kind: "New word"),
            .init(written: "grazie", translation: "thank you", explanation: "A common expression of gratitude.", pronunciation: "GRAHT-tsyeh", kind: "New word")
        ],
        "pt": [
            .init(written: "Ã", translation: "A with tilde", explanation: "The tilde marks nasalization in Portuguese vowels.", pronunciation: "ã · nasal ah", kind: "New character"),
            .init(written: "A", translation: "letter A", explanation: "Portuguese A may be open or reduced depending on stress.", pronunciation: "ah", kind: "New character"),
            .init(written: "Ç", translation: "c-cedilla", explanation: "Before a, o, u it marks an s sound.", pronunciation: "seh", kind: "New character"),
            .init(written: "olá", translation: "hello", explanation: "A friendly greeting; stress falls on the final syllable.", pronunciation: "oh-LAH", kind: "New word"),
            .init(written: "obrigado", translation: "thank you", explanation: "Traditionally said by a male speaker; obrigada by a female speaker.", pronunciation: "oh-bree-GAH-doo", kind: "New word")
        ],
        "hi": [
            .init(written: "क", translation: "ka", explanation: "Devanagari consonant क, commonly transliterated ka.", pronunciation: "ka", kind: "New character"),
            .init(written: "म", translation: "ma", explanation: "Devanagari consonant म, commonly transliterated ma.", pronunciation: "ma", kind: "New character"),
            .init(written: "अ", translation: "a", explanation: "Independent Devanagari vowel अ.", pronunciation: "a", kind: "New character"),
            .init(written: "नमस्ते", translation: "hello; greetings", explanation: "A respectful greeting used on meeting someone.", pronunciation: "namaste", kind: "New word"),
            .init(written: "धन्यवाद", translation: "thank you", explanation: "A formal expression of thanks.", pronunciation: "dhanyavaad", kind: "New word")
        ]
    ]

    static func next(language: String, step: Int) -> LessonCard {
        let cards = lessons[language] ?? lessons["ja"]!
        let index = ((step % cards.count) + cards.count) % cards.count
        return cards[index]
    }
}
