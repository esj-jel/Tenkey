package dev.localkeyboard.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LanguageCatalogTest {
    @Test fun includesExactly111UniqueLanguageLabels() {
        assertEquals(111, LanguageCatalog.all.size)
        assertEquals(111, LanguageCatalog.all.map { it.tag }.toSet().size)
    }

    @Test fun includesRequiredLanguageVarieties() {
        val tags = LanguageCatalog.all.map { it.tag }
        assertTrue("ja" in tags)
        assertTrue("yue" in tags)
        assertTrue("ko" in tags)
        assertTrue("zh-Hans" in tags)
        assertTrue("zh-Hant" in tags)
    }

    @Test fun bundledLessonsCoverRequiredLanguageVarieties() {
        assertTrue(LearningContent.supportedLessonTags().containsAll(setOf("ja", "yue", "ko", "zh-Hans", "zh-Hant", "en", "es", "fr", "de", "it", "pt", "hi")))
        assertEquals(12, LearningContent.supportedLessonTags().size)
        assertEquals("ja", LanguageCatalog.default.tag)
    }

    @Test fun dailyLessonHasAllRequestedFieldsAndIsStableForTheDay() {
        val card = LearningContent.cardForDay("ja")
        assertEquals(card, LearningContent.cardForDay("ja"))
        assertTrue(card.written.isNotBlank())
        assertTrue(card.translation.isNotBlank())
        assertTrue(card.explanation.isNotBlank())
        assertTrue(card.pronunciation.isNotBlank())
    }

    @Test fun eachOfflinePackPresentsCharactersBeforeWords() {
        LearningContent.supportedLessonTags().forEach { tag ->
            val kinds = LearningContent.cards(tag).map { it.kind }
            val firstWord = kinds.indexOf(LessonKind.WORD)
            assertTrue("$tag should include character cards", firstWord > 0)
            assertTrue("$tag should not return to characters after words", kinds.drop(firstWord).all { it == LessonKind.WORD })
        }
    }

    @Test fun unbundledLanguagesUseJapaneseOfflineFallback() {
        assertEquals(LearningContent.next("ja", 0), LearningContent.next("sv", 0))
        assertEquals(LearningContent.next("ja", 0), LearningContent.next("haw", 0))
    }
}
