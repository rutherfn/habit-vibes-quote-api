package com.nicholas.rutherford.habit.vibes.quote

import org.jetbrains.exposed.sql.Table

object PendingQuotes : Table("pending_quotes") {
    val id = integer("id").autoIncrement()
    val text = text("text")
    val author = text("author")
    val quoteSource = text("quotesource")
    val tags = text("tags")
    val createdAt = text("createdat")
    val loggedBy = text("loggedby").nullable()

    override val primaryKey = PrimaryKey(id)
}

object Quotes : Table("quotes") {
    val id = integer("id").autoIncrement()
    val text = text("text")
    val author = text("author")
    val quoteSource = text("quotesource")
    val tags = text("tags")
    val createdAt = text("createdat")
    val loggedBy = text("loggedby").nullable()

    override val primaryKey = PrimaryKey(id)
}