package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.repository.FakeTestQuoteRepository
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = FakeTestQuoteRepository

    configureSerialization(repository)
    configureDatabases()
    configureRouting()
}
