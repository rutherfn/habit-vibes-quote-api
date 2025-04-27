package com.nciholas.rutherford.habit.vibes.quote.routes

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import com.nciholas.rutherford.habit.vibes.quote.repository.FakeTestQuoteRepository
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*

fun Routing.postQuote() {
    route("/quotes") {
        post() {
            try {
                val quote = call.receive<Quote>()
                FakeTestQuoteRepository.addQuote(quote)
                call.respond(HttpStatusCode.Created)
            } catch (ex: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}