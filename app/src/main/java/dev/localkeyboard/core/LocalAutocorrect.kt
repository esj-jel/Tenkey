package dev.localkeyboard.core

import android.content.Context

/** Offline autocorrect seam. Add .dic/.aff pairs in assets/dictionaries/{language}. */
class LocalAutocorrect(private val context: Context) {
    private val cache = mutableMapOf<String, Set<String>>()

    fun suggest(languageTag: String, word: String): List<String> {
        if (word.isBlank()) return emptyList()
        val dictionary = loadWords(languageTag)
        if (word in dictionary) return emptyList()
        // Safe baseline until a full Hunspell engine is wired in: local prefix suggestions only.
        return dictionary.asSequence().filter { it.startsWith(word, ignoreCase = true) }
            .take(3).toList()
    }

    private fun loadWords(languageTag: String): Set<String> = cache.getOrPut(languageTag) {
        runCatching {
            context.assets.open("dictionaries/$languageTag.dic").bufferedReader().useLines { lines ->
                lines.drop(1).map { it.substringBefore("/").trim() }.filter(String::isNotBlank).toSet()
            }
        }.getOrDefault(emptySet())
    }
}
