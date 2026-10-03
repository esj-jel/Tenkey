package dev.localkeyboard.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LanguageCatalogTest {
    @Test fun includesExactly55Languages() = assertEquals(55, LanguageCatalog.all.size)
    @Test fun requiredLanguagesExist() {
        val tags = LanguageCatalog.all.map { it.tag }
        assertTrue("ja" in tags); assertTrue("en" in tags); assertTrue("ar" in tags); assertTrue("sv" in tags)
    }
    @Test fun defaultIsJapanese() = assertEquals("ja", LanguageCatalog.default.tag)
}
