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
        assertTrue(LearningContent.supportedLessonTags().containsAll(setOf("ja", "yue", "ko", "zh-Hans", "zh-Hant")))
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
}
