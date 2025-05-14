package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestPendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestQuoteRepository
import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class RoutingTest {
    private val quoteRepository = TestQuoteRepository()
    private val pendingQuoteRepository = TestPendingQuoteRepository(quoteRepository = quoteRepository)

    private val json =
        Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }

    private val dotenv = dotenv()
    private val publicAccessToken = dotenv["DB_AUTHENTICATION_PUBLIC_ACCESS_TOKEN"]
    private val privateAccessToken = dotenv["DB_AUTHENTICATION_PRIVATE_ACCESS_TOKEN"]

    private fun Application.setup() {
        install(ContentNegotiation) { json(json) }
        configureAuthentication() // Ensure authentication is configured
        configureRouting(quoteRepository = quoteRepository, pendingQuoteRepository = pendingQuoteRepository)
    }

    @Test
    fun `get quotes`() =
        testApplication {
            application { setup() }
            val response = client.get("/quotes") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.OK, response.status)
            val quotes = json.decodeFromString<List<Quote>>(response.bodyAsText())
            assertEquals(10, quotes.size)
        }

    @Test
    fun `get pending quotes`() =
        testApplication {
            application { setup() }
            val response = client.get("/pending/quotes") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.OK, response.status)
            val quotes = json.decodeFromString<List<Quote>>(response.bodyAsText())
            assertEquals(4, quotes.size)
        }

    @Test
    fun `get quotes with empty list`() =
        testApplication {
            val quoteRepository = TestQuoteRepository(overrideQuotes = mutableListOf())
            val pendingQuoteRepository = TestPendingQuoteRepository(quoteRepository = quoteRepository)
            application {
                install(ContentNegotiation) { json(json) }
                configureAuthentication()
                configureRouting(
                    quoteRepository = quoteRepository,
                    pendingQuoteRepository = pendingQuoteRepository,
                )
            }
            val response = client.get("/quotes") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.NotFound, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("No quotes found", error.error)
        }

    @Test
    fun `get quotes with empty pending list`() =
        testApplication {
            application {
                install(ContentNegotiation) { json(json) }
                configureAuthentication()
                configureRouting(
                    quoteRepository = quoteRepository,
                    pendingQuoteRepository =
                        TestPendingQuoteRepository(
                            overrideQuotes = mutableListOf(),
                            quoteRepository = quoteRepository,
                        ),
                )
            }
            val response = client.get("/pending/quotes") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.NotFound, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("No pending quotes found", error.error)
        }

    @Test
    fun `post single quote`() =
        testApplication {
            application { setup() }
            val newQuote = Quote(11, "Stay hungry", "Steve Jobs", "Speech", listOf("motivation"), "2025-04-27T11:00:00Z", "user999")
            val response = client.post("/quotes") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(newQuote))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(11, quoteRepository.getAllQuotes().size)
        }

    @Test
    fun `post single pending quote`() =
        testApplication {
            application { setup() }
            val newQuote = Quote(11, "Stay hungry", "Steve Jobs", "Speech", listOf("motivation"), "2025-04-27T11:00:00Z", "user999")
            val response = client.post("/pending/quotes") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(newQuote))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(5, pendingQuoteRepository.getAllPendingQuotes().size)
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
            val response = client.post("/quotes") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(newQuotes))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(12, quoteRepository.getAllQuotes().size)
        }

    @Test
    fun `post multiple pending quotes`() =
        testApplication {
            application { setup() }
            val newQuotes =
                listOf(
                    Quote(12, "Quote 1", "Author A", "Source A", listOf("tag1"), "2025-05-01T10:00:00Z", "user1"),
                    Quote(13, "Quote 2", "Author B", "Source B", listOf("tag2"), "2025-05-02T10:00:00Z", "user2"),
                )
            val response = client.post("/pending/quotes") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(newQuotes))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(6, pendingQuoteRepository.getAllPendingQuotes().size)
        }

    @Test
    fun `post quote with malformed json`() =
        testApplication {
            application { setup() }
            val malformedJson = """{ "id": "abc", "title": "oops" }"""
            val response = client.post("/quotes") {
                contentType(ContentType.Application.Json)
                setBody(malformedJson)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    fun `post pending quote with malformed json`() =
        testApplication {
            application { setup() }
            val malformedJson = """{ "id": "abc", "title": "oops" }"""
            val response = client.post("/pending/quotes") {
                contentType(ContentType.Application.Json)
                setBody(malformedJson)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.BadRequest, response.status)
        }

    @Test
    fun `delete a quote`() =
        testApplication {
            application { setup() }
            val quoteToDelete = quoteRepository.getAllQuotes().first()
            val response = client.delete("/quotes") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(quoteToDelete))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.OK, response.status)
            val remaining = json.decodeFromString<List<Quote>>(response.bodyAsText())
            assertEquals(9, remaining.size)
        }

    @Test
    fun `get quote by title`() =
        testApplication {
            application { setup() }
            val title = quoteRepository.getAllQuotes().first().quoteText
            val response = client.get("/quotes/search/$title") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.OK, response.status)
            val quote = json.decodeFromString<Quote>(response.bodyAsText())
            assertEquals(title, quote.quoteText)
        }

    @Test
    fun `get pending quote by title`() =
        testApplication {
            application { setup() }
            val title = pendingQuoteRepository.getAllPendingQuotes().first().quoteText
            val response = client.get("pending//quotes/search/$title") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.OK, response.status)
            val quote = json.decodeFromString<Quote>(response.bodyAsText())
            assertEquals(title, quote.quoteText)
        }

    @Test
    fun `get quote by title not found`() =
        testApplication {
            application { setup() }
            val response = client.get("/quotes/search/nonexistent-title") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.NotFound, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("Quote not found", error.error)
        }

    @Test
    fun `get pending quote by title not found`() =
        testApplication {
            application { setup() }
            val response = client.get("/pending/quotes/search/nonexistent-title") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.NotFound, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("Quote not found", error.error)
        }

    @Test
    fun `get quote by title when title is null`() =
        testApplication {
            application { setup() }
            val response = client.get("/quotes/search/") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.BadRequest, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("Missing title", error.error)
        }

    @Test
    fun `get pending quote by title when title is null`() =
        testApplication {
            application { setup() }
            val response = client.get("/pending/quotes/search/") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $publicAccessToken")
                }
            }
            assertEquals(HttpStatusCode.BadRequest, response.status)
            val error = json.decodeFromString<ErrorResponse>(response.bodyAsText())
            assertEquals("Missing title", error.error)
        }

    @Test
    fun `promote one pending quote to regular quotes`() =
        testApplication {
            application { setup() }
            val newQuote =
                Quote(
                    id = 21,
                    quoteText = "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                    author = "Ralph Waldo Emerson",
                    quoteSource = "Essay",
                    tags = listOf("strength", "character", "potential"),
                    createdAt = "2025-04-27T10:10:00Z",
                    loggedBy = "user123",
                )

            val response = client.post("pending/quotes/promote") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(newQuote))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }

            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(4, pendingQuoteRepository.getAllPendingQuotes().size)
            assertEquals(11, quoteRepository.getAllQuotes().size)
        }

    @Test
    fun `promote a list of pending quotes to regular quotes`() =
        testApplication {
            application { setup() }
            val newQuotes =
                listOf(
                    Quote(
                        id = 21,
                        quoteText = "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                        author = "Ralph Waldo Emerson",
                        quoteSource = "Essay",
                        tags = listOf("strength", "character", "potential"),
                        createdAt = "2025-04-27T10:10:00Z",
                        loggedBy = "user123",
                    ),
                    Quote(
                        id = 22,
                        quoteText = "Act as if what you do makes a difference. It does.",
                        author = "William James",
                        quoteSource = "Lecture",
                        tags = listOf("impact", "life", "action"),
                        createdAt = "2025-04-27T10:15:00Z",
                        loggedBy = "user456",
                    ),
                )
            val response = client.post("pending/quotes/promote") {
                contentType(ContentType.Application.Json)
                setBody(json.encodeToString(newQuotes))
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }

            assertEquals(HttpStatusCode.Created, response.status)
            assertEquals(4, pendingQuoteRepository.getAllPendingQuotes().size)
            assertEquals(12, quoteRepository.getAllQuotes().size)
        }

    @Test
    fun `promote post pending quote with malformed json`() =
        testApplication {
            application { setup() }
            val malformedJson = """{ "id": "abc", "title": "oops" }"""
            val response = client.post("pending/quotes/promote") {
                contentType(ContentType.Application.Json)
                setBody(malformedJson)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $privateAccessToken")
                }
            }
            assertEquals(HttpStatusCode.BadRequest, response.status)
        }
}
