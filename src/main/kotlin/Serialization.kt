package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.repository.FakeTestQuoteRepository
import com.nciholas.rutherford.habit.vibes.quote.routes.getQuotes
import com.nciholas.rutherford.habit.vibes.quote.routes.postQuote
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json


fun Application.configureSerialization(repository: FakeTestQuoteRepository) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
}
