package com.nciholas.rutherford.habit.vibes.quote

import com.nciholas.rutherford.habit.vibes.quote.repository.TestQuoteRepository
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = TestQuoteRepository()
   // val jsonReader = JsonReader() todo -> Used to enable test data vs database data


    configureSerialization()
    configureDatabases()
    configureRouting(quoteRepository = repository)
}
