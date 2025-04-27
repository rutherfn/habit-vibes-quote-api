package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.routes.getQuotes
import com.nciholas.rutherford.habit.vibes.quote.routes.postQuote
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("static", "static")
        getQuotes()
        postQuote()
    }
}
