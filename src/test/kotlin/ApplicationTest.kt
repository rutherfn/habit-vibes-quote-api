package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import com.nciholas.rutherford.habit.vibes.quote.repository.test.TestQuoteRepository
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.testing.testApplication
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    private val quoteRepository = TestQuoteRepository()
    private val json =
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

    private fun Application.setup() {
        install(ContentNegotiation) { json(json) }
        configureRouting(quoteRepository)
    }

    @Test
    fun `get quotes`() =
        testApplication {
            application { setup() }
            val response = client.get("/quotes")
            assertEquals(HttpStatusCode.OK, response.status)
            val quotes = json.decodeFromString<List<Quote>>(response.bodyAsText())
            assertEquals(10, quotes.size)
        }

    @Test
    fun `get quotes with empty list`() =
        testApplication {
            application {
                install(ContentNegotiation) { json(json) }
                configureRouting(TestQuoteRepository(overrideQuotes = mutableListOf()))
            }
            val response = client.get("/quotes")
            assertEquals(HttpStatusCode.NotFound, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("No quotes found", error.error)
        }

    @Test
    fun `post single quote`() =
        testApplication {
            application { setup() }
            val newQuote = Quote(11, "Stay hungry", "Steve Jobs", "Speech", listOf("motivation"), "2025-04-27T11:00:00Z", "user999")
            val response =
                client.post("/quotes") {
                    contentType(ContentType.Application.Json)
                    setBody(json.encodeToString(newQuote))
                }
            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(11, quoteRepository.getAllQuotes().size)
        }

    @Test
    fun `post multiple quotes`() =
        testApplication {
            application { setup() }
            val newQuotes =
                listOf(
                    Quote(12, "Quote 1", "Author A", "Source A", listOf("tag1"), "2025-05-01T10:00:00Z", "user1"),
                    Quote(13, "Quote 2", "Author B", "Source B", listOf("tag2"), "2025-05-02T10:00:00Z", "user2"),
                )
            val response =
                client.post("/quotes") {
                    contentType(ContentType.Application.Json)
                    setBody(json.encodeToString(newQuotes))
                }
            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(12, quoteRepository.getAllQuotes().size)
        }

    @Test
    fun `post quote with malformed json`() =
        testApplication {
            application { setup() }
            val malformedJson = """{ "id": "abc", "title": "oops" }"""
            val response =
                client.post("/quotes") {
                    contentType(ContentType.Application.Json)
                    setBody(malformedJson)
                }
            assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    fun `delete a quote`() =
        testApplication {
            application { setup() }
            val quoteToDelete = quoteRepository.getAllQuotes().first()
            val response =
                client.delete("/quotes") {
                    contentType(ContentType.Application.Json)
                    setBody(json.encodeToString(quoteToDelete))
                }
            assertEquals(HttpStatusCode.OK, response.status)
            val remaining = json.decodeFromString<List<Quote>>(response.bodyAsText())
            assertEquals(9, remaining.size)
        }

    @Test
    fun `get quote by title`() =
        testApplication {
            application { setup() }
            val title = quoteRepository.getAllQuotes().first().title
            val response = client.get("/quotes/search/$title")
            assertEquals(HttpStatusCode.OK, response.status)
            val quote = json.decodeFromString<Quote>(response.bodyAsText())
            assertEquals(title, quote.title)
        }

    @Test
    fun `get quote by title not found`() =
        testApplication {
            application { setup() }
            val response = client.get("/quotes/search/nonexistent-title")
            assertEquals(HttpStatusCode.NotFound, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("Quote not found", error.error)
        }

    @Test
    fun `get quote by title when title is null`() =
        testApplication {
            application { setup() }
            val response = client.get("/quotes/search/")
            assertEquals(HttpStatusCode.BadRequest, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("Missing title", error.error)
        }
}
