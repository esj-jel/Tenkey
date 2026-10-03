package dev.localkeyboard.core

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors

class AiKeyboardDesigner {
    private val executor = Executors.newSingleThreadExecutor()

    fun generate(apiKey: String, language: String, request: String, callback: (Result<CustomKeyboardProfile>) -> Unit) {
        executor.execute {
            val result = runCatching {
                require(apiKey.isNotBlank()) { "Enter an API key first." }
                val connection = URL("https://api.openai.com/v1/chat/completions").openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.setRequestProperty("Authorization", "Bearer $apiKey")
                connection.setRequestProperty("Content-Type", "application/json")
                connection.doOutput = true
                val prompt = """
                    Design a compact Android keyboard profile for language $language.
                    User request: $request
                    Return JSON only with displayName, rows (array of space-separated key labels),
                    backgroundStart, backgroundEnd, keyColor, accentColor as #RRGGBB.
                """.trimIndent()
                val body = JSONObject().put("model", "gpt-4o-mini").put("temperature", 0.4)
                    .put("messages", org.json.JSONArray().put(JSONObject().put("role", "user").put("content", prompt)))
                    .toString()
                connection.outputStream.use { it.write(body.toByteArray(StandardCharsets.UTF_8)) }
                val response = (if (connection.responseCode in 200..299) connection.inputStream else connection.errorStream)
                    .bufferedReader().use { it.readText() }
                if (connection.responseCode !in 200..299) error("AI request failed (${connection.responseCode})")
                val content = JSONObject(response).getJSONArray("choices").getJSONObject(0).getJSONObject("message").getString("content")
                parseProfile(language, content)
            }
            callback(result)
        }
    }

    private fun parseProfile(language: String, raw: String): CustomKeyboardProfile {
        val json = JSONObject(raw.substringAfter("{").substringBeforeLast("}").let { "{$it}" })
        fun color(name: String, fallback: Int) = runCatching {
            android.graphics.Color.parseColor(json.optString(name, ""))
        }.getOrDefault(fallback)
        val rows = json.optJSONArray("rows")?.let { array -> (0 until array.length()).map { array.getString(it) } }
            ?: listOf("q w e r t", "y u i o p")
        return CustomKeyboardProfile(
            languageTag = language,
            displayName = json.optString("displayName", "AI keyboard"),
            rows = rows,
            backgroundStart = color("backgroundStart", 0xFFF7F2FF.toInt()),
            backgroundEnd = color("backgroundEnd", 0xFFE9F5FF.toInt()),
            keyColor = color("keyColor", 0xFFFFFFFF.toInt()),
            accentColor = color("accentColor", 0xFF6750A4.toInt())
        )
    }
}
