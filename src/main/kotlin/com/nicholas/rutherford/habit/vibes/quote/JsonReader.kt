package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.model.Toggle
import com.nicholas.rutherford.habit.vibes.quote.model.ToggleList
import kotlinx.serialization.json.Json
import java.io.File

class JsonReader {
    fun readEnableToggles(path: String): List<Toggle> {
        val toggleArrayList: ArrayList<Toggle> = arrayListOf()

        val file = File(path)

        if (!file.exists() || file.isDirectory) {
            println("Cannot grab toggles when file exists is ${file.exists()} and isDirectory is set to ${file.isDirectory}")
            return toggleArrayList
        } else {
            val json = file.readText()
            try {
                val toggleList = Json.Default.decodeFromString<ToggleList>(json)
                toggleList.toggles.forEach { toggle ->
                    toggleArrayList.add(toggle)
                }
                return toggleArrayList
            } catch (e: Exception) {
                println("Failed to parse JSON: ${e.message}")
                return toggleArrayList
            }
        }
    }
}
