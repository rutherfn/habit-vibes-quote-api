package com.nicholas.rutherford.habit.vibes.quote.repository.postgres

import com.nicholas.rutherford.habit.vibes.quote.PendingQuotes
import com.nicholas.rutherford.habit.vibes.quote.Quotes
import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.PendingQuoteRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PendingQuoteRepositoryImpl : PendingQuoteRepository {
    override suspend fun getAllPendingQuotes(): List<Quote> {
        return transaction {
            return@transaction PendingQuotes.selectAll().map {
                Quote(
                    id = it[PendingQuotes.id],
                    title = it[PendingQuotes.text],
                    author = it[PendingQuotes.author],
                    quoteSource = it[PendingQuotes.quoteSource],
                    tags = it[PendingQuotes.tags].split(", "),
                    createdAt = it[PendingQuotes.createdAt],
                    loggedBy = it[PendingQuotes.loggedBy]
                )
            }
        }
    }

    override suspend fun postQuote(quote: Quote) {
        transaction {
            PendingQuotes.insert {
                it[text] = quote.title
                it[author] = quote.author
                it[quoteSource] = quote.quoteSource
                it[tags] = quote.tags.joinToString(", ")
                it[createdAt] = quote.createdAt
                it[loggedBy] = quote.loggedBy
            }
        }
    }

    override suspend fun postQuotes(quotes: List<Quote>) {
        transaction {
            quotes.forEach { quote ->
                PendingQuotes.insert {
                    it[text] = quote.title
                    it[author] = quote.author
                    it[quoteSource] = quote.quoteSource
                    it[tags] = quote.tags.joinToString(", ")
                    it[createdAt] = quote.createdAt
                    it[loggedBy] = quote.loggedBy
                }
            }
        }
    }

    override suspend fun getQuoteByTitle(title: String): Quote? {
        return transaction {
            val cleanedTitle = title.trim().lowercase()

            val quotes = PendingQuotes
                .selectAll() //todo -> Figure out a way we can just select what we need vs everything
                .map {
                    Quote(
                        id = it[PendingQuotes.id],
                        title = it[PendingQuotes.text],
                        author = it[PendingQuotes.author],
                        quoteSource = it[PendingQuotes.quoteSource],
                        tags = it[PendingQuotes.tags].split(", "),
                        createdAt = it[PendingQuotes.createdAt],
                        loggedBy = it[PendingQuotes.loggedBy]
                    )
                }

            return@transaction quotes.firstOrNull { it.title.lowercase() == cleanedTitle }
        }
    }

    override suspend fun removeQuote(quote: Quote) {
        transaction {
            PendingQuotes.deleteWhere { Quotes.id eq quote.id }
        }
    }

    override suspend fun promoteQuote(quote: Quote) {
        val currentQuotes = Quotes.selectAll().map {
            Quote(
                id = it[Quotes.id],
                title = it[Quotes.text],
                author = it[Quotes.author],
                quoteSource = it[Quotes.quoteSource],
                tags = it[Quotes.tags].split(", "),
                createdAt = it[Quotes.createdAt],
                loggedBy = it[Quotes.loggedBy]
            )
        }

        if (!currentQuotes.contains(quote)) {
            Quotes.insert {
                it[text] = quote.title
                it[author] = quote.author
                it[quoteSource] = quote.quoteSource
                it[tags] = quote.tags.joinToString(", ")
                it[createdAt] = quote.createdAt
                it[loggedBy] = quote.loggedBy
            }
        }
    }

    override suspend fun promoteQuotes(quotes: List<Quote>) {
        val currentQuotes = Quotes.selectAll().map {
            Quote(
                id = it[Quotes.id],
                title = it[Quotes.text],
                author = it[Quotes.author],
                quoteSource = it[Quotes.quoteSource],
                tags = it[Quotes.tags].split(", "),
                createdAt = it[Quotes.createdAt],
                loggedBy = it[Quotes.loggedBy]
            )
        }

        val quotesToPost = quotes.filterNot { it in currentQuotes }

        quotesToPost.forEach { quote ->
            Quotes.insert {
                it[text] = quote.title
                it[author] = quote.author
                it[quoteSource] = quote.quoteSource
                it[tags] = quote.tags.joinToString(", ")
                it[createdAt] = quote.createdAt
                it[loggedBy] = quote.loggedBy
            }
        }
    }
}