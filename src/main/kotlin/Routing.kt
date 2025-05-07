package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import com.nciholas.rutherford.habit.vibes.quote.repository.QuoteRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureRouting(quoteRepository: QuoteRepository) {
    routing {
        install(StatusPages) {
            exception<ContentTransformationException> { call, cause ->
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse("Malformed request", cause.localizedMessage)
                )
            }
            exception<IllegalStateException> { call, cause ->
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse("Internal server error", cause.localizedMessage)
                )
            }
            exception<Throwable> { call, cause ->
                call.respond(
                    HttpStatusCode.ServiceUnavailable,
                    ErrorResponse("Unexpected error", cause.localizedMessage)
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
                    call.respond(status = HttpStatusCode.BadRequest, ErrorResponse("Invalid request", ex.localizedMessage ?: ""))
                }
            }

            delete {
                val quote = call.receive<Quote>()
                quoteRepository.removeQuote(quote)
                call.respond(quoteRepository.getAllQuotes())
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
    }
}

