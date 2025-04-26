package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        staticResources("static", "static")
        get("/") {
            call.respond(
                listOf(
                    Quote(
                        id = 4,
                        author = "test",
                        source = "test1",
                        tags = listOf("tag1", "tag2"),
                        createdAt = "test createdat",
                        loggedBy = null,
                    ),
                ),
            )
        }
    }
}
