package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.repository.postgres.PendingQuoteRepositoryImpl
import com.nicholas.rutherford.habit.vibes.quote.repository.postgres.QuoteRepositoryImpl
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestPendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestQuoteRepository
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        module()
    }.start(wait = true)
}

fun Application.module() {
    val isTestEnabled =
        JsonReader()
            .readEnableToggles(path = "toggles/enable_toggles.json")
            .first().enabled

    val quoteRepository =
        if (isTestEnabled) {
            TestQuoteRepository()
        } else {
            QuoteRepositoryImpl()
        }

    val pendingQuoteRepository =
        if (isTestEnabled) {
            TestPendingQuoteRepository(quoteRepository = quoteRepository)
        } else {
            PendingQuoteRepositoryImpl(quoteRepository = quoteRepository)
        }

    configureSerialization()

    if (!isTestEnabled) {
        configureDatabases()
    }
    configureAuthentication()

    configureRouting(
        quoteRepository = quoteRepository,
        pendingQuoteRepository = pendingQuoteRepository,
    )
}
