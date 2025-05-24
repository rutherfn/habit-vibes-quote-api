package com.nicholas.rutherford.habit.vibes.quote

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val db = Database.connect(
        url = "jdbc:postgresql://cc0gj7hsrh0ht8.cluster-czrs8kj4isg7.us-east-1.rds.amazonaws.com:5432/d4623fnqki6jkb?sslmode=require",
        driver = "org.postgresql.Driver",
        user = "uffbh3ne3p5lc8",
        password = "p1f7b1e85ec4e5f2dca14c04b9b2df77a74cfa5ef746c8b97f41f4aedb78d10bc"
    )


    checkForDbConnection(db = db)
}

private fun checkForDbConnection(db: Database) {
    try {
        transaction(db) {
            exec("SELECT 1") { rs ->
                if (rs.next()) {
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
