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

fun Application.configureRouting(quoteRepository: QuoteRepository) {
    routing {
        staticResources("static", "static")

        swaggerUI(path = "swagger", swaggerFile = "openapi/documentation.yaml")

        install(StatusPages) {
            exception<Throwable> { call, cause ->
                call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }

        route("/quotes") {
            get {
                call.respond(quoteRepository.getAllQuotes())
            }
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
            post {
                try {
                    quoteRepository.postQuotes(quotes = call.receive<List<Quote>>())
                    call.respond(HttpStatusCode.Created)
                } catch (ex: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete {
                quoteRepository.removeQuote(quote = call.receive<Quote>())
                call.respond(quoteRepository.getAllQuotes())
            }
        }

        route("/quotes/{title}") {
            get {
                val title = call.parameters["title"]

                if (title != null) {
                    quoteRepository.getQuoteByTitle(title = title)?.let { quoteByName ->
                        call.respond(quoteByName)
                    }
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}
