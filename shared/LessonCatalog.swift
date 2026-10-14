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
        ]
    ]

    static func next(language: String, step: Int) -> LessonCard {
        let cards = lessons[language] ?? lessons["ja"]!
        return cards[abs(step) % cards.count]
    }
}
