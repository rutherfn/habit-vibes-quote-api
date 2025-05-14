package com.nicholas.rutherford.habit.vibes.quote

import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val dotenv = dotenv()
    val db =
        Database.connect(
            url = dotenv["DB_URL"],
            driver = dotenv["DB_DRIVER"],
            user = dotenv["DB_USER"],
            password = dotenv["DB_PASSWORD"],
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
