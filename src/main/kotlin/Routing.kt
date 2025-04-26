package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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
                        loggedBy = null
                    )
                )
            )
        }

    }
}
