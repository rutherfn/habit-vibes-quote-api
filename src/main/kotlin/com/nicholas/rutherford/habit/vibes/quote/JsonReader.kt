package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.model.Toggle
import com.nicholas.rutherford.habit.vibes.quote.model.ToggleList
import kotlinx.serialization.json.Json
import java.io.InputStream

class JsonReader {
    fun readEnableToggles(path: String): List<Toggle> {
        val toggleArrayList = arrayListOf<Toggle>()

        val inputStream: InputStream? = this::class.java.classLoader.getResourceAsStream(path)

        if (inputStream == null) {
            println("Resource not found: $path")
            return toggleArrayList
        }

        return try {
            val json = inputStream.bufferedReader().use { it.readText() }
            val parser = Json { ignoreUnknownKeys = true }
            parser.decodeFromString<ToggleList>(json).toggles
        } catch (e: Exception) {
            println("Failed to parse JSON: ${e.message}")
            toggleArrayList
        }
    }
}
