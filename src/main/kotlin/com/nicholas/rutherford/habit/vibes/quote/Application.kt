package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestPendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestQuoteRepository
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val quoteRepository = TestQuoteRepository()
    val pendingQuoteRepository = TestPendingQuoteRepository(quoteRepository = quoteRepository)
    // val jsonReader = JsonReader() todo -> Used to enable test data vs database data

    configureSerialization()
    configureDatabases()
    configureRouting(quoteRepository = quoteRepository, pendingQuoteRepository = pendingQuoteRepository)
}
