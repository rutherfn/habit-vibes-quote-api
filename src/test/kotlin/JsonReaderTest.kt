package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.model.Toggle
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonReaderTest {
    val reader = JsonReader()

    @Test
    fun `readEnableToggles should parse valid json`() {
        val path = "toggles/enable_toggles.json"

        val toggles = reader.readEnableToggles(path)

        assertEquals(1, toggles.size)
        assertEquals(
            Toggle(
                id = 1,
                enabled = true,
                name = "testDataEnabled",
            ),
            toggles[0],
        )
    }

    @Test
    fun `toggles should return empty list if file not found`() {
        val path = "nonexistent/file.json"

        val toggles = reader.readEnableToggles(path)

        assertEquals(0, toggles.size)
    }

    @Test
    fun `readEnableToggles should handle malformed json`() {
        val path = "toggles/malformed_enable_toggles.json"
        val toggles = reader.readEnableToggles(path)

        assertEquals(0, toggles.size)
    }
}
