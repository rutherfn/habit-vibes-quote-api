package com.nicholas.rutherford.habit.vibes.quote

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val db =
        Database.connect(
            url = System.getenv("DB_URL"),
            driver = System.getenv("DB_DRIVER"),
            user = System.getenv("DB_USER"),
            password = System.getenv("DB_PASSWORD"),
        )

    checkForDbConnection(db = db)
}

private fun checkForDbConnection(db: Database) {
    try {
        transaction(db) {
            exec("SELECT 1") { rs ->
                if (rs?.next() == true) {
                    println("Database connected successfully.")
                    transaction {
                        SchemaUtils.create(PendingQuotes, Quotes)
                    }
                } else {
                    println("Database connection check failed.")
                }
            }
        }
    } catch (e: Exception) {
        println("Failed to connect to database: ${e.localizedMessage}")
    }
}
