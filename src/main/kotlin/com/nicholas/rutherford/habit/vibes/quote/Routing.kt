package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.PendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.QuoteRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.ContentTransformationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.request.receive
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun Application.configureRouting(
    quoteRepository: QuoteRepository,
    pendingQuoteRepository: PendingQuoteRepository,
) {
    routing {
        install(StatusPages) {
            exception<ContentTransformationException> { call, cause ->
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse("Malformed request", cause.localizedMessage),
                )
            }
            exception<IllegalStateException> { call, cause ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse("Internal server error", cause.localizedMessage),
                )
            }
            exception<Throwable> { call, cause ->
                call.respond(
                    HttpStatusCode.ServiceUnavailable,
                    ErrorResponse("Unexpected error", cause.localizedMessage),
                )
            }
        }

        staticResources("static", "static")
        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        route("/quotes") {
            get {
                val quotes = quoteRepository.getAllQuotes()
                if (quotes.isEmpty()) {
                    call.respond(status = HttpStatusCode.NotFound, message = ErrorResponse("No quotes found"))
                } else {
                    println("here are the quotes $quotes")
                    call.respond(status = HttpStatusCode.OK, message = quotes)
                }
            }

            post {
                val bodyText = call.receiveText()

                try {
                    if (bodyText.trim().startsWith(prefix = "[")) {
                        val quotes = Json.decodeFromString<List<Quote>>(bodyText)
                        quoteRepository.postQuotes(quotes)
                    } else {
                        val quote = Json.decodeFromString<Quote>(bodyText)
                        quoteRepository.postQuote(quote)
                    }
                    call.respond(HttpStatusCode.Created)
                } catch (ex: Exception) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message =
                            ErrorResponse(
                                error = "Invalid request",
                                details = ex.localizedMessage ?: "",
                            ),
                    )
                }
            }

            delete {
                val quote = call.receive<Quote>()
                quoteRepository.removeQuote(quote)
                call.respond(quoteRepository.getAllQuotes())
                println(quoteRepository.getAllQuotes())
            }
        }

        get("/quotes/search/{title?}") {
            val title = call.parameters["title"]
            if (title != null) {
                val quote = quoteRepository.getQuoteByTitle(title)
                if (quote != null) {
                    call.respond(quote)
                } else {
                    call.respond(status = HttpStatusCode.NotFound, ErrorResponse("Quote not found"))
                }
            } else {
                call.respond(status = HttpStatusCode.BadRequest, ErrorResponse("Missing title"))
            }
        }

        route("/pending/quotes") {
            get {
                val quotes = pendingQuoteRepository.getAllPendingQuotes()
                if (quotes.isEmpty()) {
                    call.respond(status = HttpStatusCode.NotFound, message = ErrorResponse("No pending quotes found"))
                } else {
                    call.respond(status = HttpStatusCode.OK, message = quotes)
                }
            }

            post {
                val bodyText = call.receiveText()

                try {
                    if (bodyText.trim().startsWith(prefix = "[")) {
                        val quotes = Json.decodeFromString<List<Quote>>(bodyText)
                        pendingQuoteRepository.postQuotes(quotes)
                    } else {
                        val quote = Json.decodeFromString<Quote>(bodyText)
                        pendingQuoteRepository.postQuote(quote)
                    }
                    call.respond(HttpStatusCode.Created)
                } catch (ex: Exception) {
                    call.respond(
                        status = HttpStatusCode.BadRequest,
                        message =
                            ErrorResponse(
                                error = "Invalid request",
                                details = ex.localizedMessage ?: "",
                            ),
                    )
                }
            }

            delete {
                val quote = call.receive<Quote>()
                pendingQuoteRepository.removeQuote(quote)
                call.respond(pendingQuoteRepository.getAllPendingQuotes())
            }
        }

        get("pending/quotes/search/{title?}") {
            val title = call.parameters["title"]
            if (title != null) {
                val quote = pendingQuoteRepository.getQuoteByTitle(title)
                if (quote != null) {
                    call.respond(quote)
                } else {
                    call.respond(
                        status = HttpStatusCode.NotFound,
                        message = ErrorResponse(error = "Quote not found"),
                    )
                }
            } else {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = ErrorResponse(error = "Missing title"),
                )
            }
        }

        post("pending/quotes/promote") {
            val bodyText = call.receiveText()

            try {
                if (bodyText.trim().startsWith(prefix = "[")) {
                    val quotes = Json.decodeFromString<List<Quote>>(bodyText)
                    pendingQuoteRepository.promoteQuotes(quotes = quotes)
                } else {
                    val quote = Json.decodeFromString<Quote>(bodyText)
                    pendingQuoteRepository.promoteQuote(quote = quote)
                }
                call.respond(HttpStatusCode.Created)
            } catch (ex: Exception) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message =
                        ErrorResponse(
                            error = "Invalid request",
                            details = ex.localizedMessage ?: "",
                        ),
                )
            }
        }
    }
}
