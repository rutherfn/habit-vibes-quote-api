package com.nciholas.rutherford.habit.vibes.quote.routes

import com.nciholas.rutherford.habit.vibes.quote.repository.QuoteRepository
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.getQuotes() {
    get("/quotes") {
        val quotes = QuoteRepository.allQuotes()
        call.respond(quotes)
    }
}
