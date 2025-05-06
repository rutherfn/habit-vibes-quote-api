package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import com.nciholas.rutherford.habit.vibes.quote.repository.TestQuoteRepository
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private val quoteRepository = TestQuoteRepository()

    @Test
    fun `get quotes`() = testApplication {
        val allQuotes = quoteRepository.getAllQuotes()

        application {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }

            routing {
                route("/quotes") {
                    get {
                        call.respond(allQuotes)
                    }
                }
            }
        }

        val response = client.get("/quotes")

        assertEquals(HttpStatusCode.OK, response.status)

        val responseBody = response.bodyAsText()
        val quotes = Json.decodeFromString<List<Quote>>(responseBody)

        assertEquals(10, quotes.size)
        assertEquals(allQuotes, quotes)
    }

    @Test
    fun `post quotes with valid quote`() = testApplication {
        application {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }

            routing {
                route("/quotes") {
                    post {
                        try {
                            quoteRepository.postQuote(quote = call.receive<Quote>())
                            call.respond(HttpStatusCode.Created)
                        } catch (ex: ContentTransformationException) {
                            call.respond(HttpStatusCode.BadRequest)
                        } catch (ex: IllegalStateException) {
                            call.respond(HttpStatusCode.BadRequest)
                        }
                    }
                }
            }
        }
        val newQuote = Quote(
            id = 11,
            title = "Stay hungry, stay foolish.",
            author = "Steve Jobs",
            source = "Speech",
            tags = listOf("motivation", "life"),
            createdAt = "2025-04-27T11:00:00Z",
            loggedBy = "user999"
        )

        val response = client.post("/quotes") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(newQuote))
        }

        assertEquals(HttpStatusCode.Created, response.status)

        val quotes = quoteRepository.getAllQuotes()

        assertEquals(quotes.size, 11)
        }
    }
