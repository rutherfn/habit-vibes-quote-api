package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.repository.postgres.PendingQuoteRepositoryImpl
import com.nicholas.rutherford.habit.vibes.quote.repository.postgres.QuoteRepositoryImpl
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestPendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestQuoteRepository
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val jsonPath = "toggles/enable_toggles.json"
    val jsonReader = JsonReader()
    val testDataToggle = jsonReader.readEnableToggles(path = jsonPath).first()

    val quoteRepository = if (testDataToggle.enabled) {
        TestQuoteRepository()
    } else {
        QuoteRepositoryImpl()
    }
    val pendingQuoteRepository = if (testDataToggle.enabled) {
        TestPendingQuoteRepository(quoteRepository = quoteRepository)
    } else {
        PendingQuoteRepositoryImpl()
    }

    configureSerialization()
    configureDatabases()
    configureRouting(quoteRepository = quoteRepository, pendingQuoteRepository = pendingQuoteRepository)
}
