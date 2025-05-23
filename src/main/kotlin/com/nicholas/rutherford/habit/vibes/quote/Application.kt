package com.nicholas.rutherford.habit.vibes.quote

import com.nicholas.rutherford.habit.vibes.quote.repository.postgres.PendingQuoteRepositoryImpl
import com.nicholas.rutherford.habit.vibes.quote.repository.postgres.QuoteRepositoryImpl
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestPendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.test.TestQuoteRepository
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val dotenv = dotenv()
    val isTestDataEnabled = dotenv["TEST_DATA_ENABLED"].toBoolean()

    val quoteRepository =
        if (isTestDataEnabled) {
            TestQuoteRepository()
        } else {
            QuoteRepositoryImpl()
        }

    val pendingQuoteRepository =
        if (isTestDataEnabled) {
            TestPendingQuoteRepository(quoteRepository = quoteRepository)
        } else {
            PendingQuoteRepositoryImpl(quoteRepository = quoteRepository)
        }

    configureSerialization()

    if (!isTestDataEnabled) {
        configureDatabases()
    }
    configureAuthentication()

    configureRouting(
        quoteRepository = quoteRepository,
        pendingQuoteRepository = pendingQuoteRepository,
    )
}
