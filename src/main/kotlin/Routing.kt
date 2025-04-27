package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.routes.getQuotes
import com.nciholas.rutherford.habit.vibes.quote.routes.postQuote
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respondText
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        staticResources("static", "static")
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }
        getQuotes()
        postQuote()
    }
}
