package com.nicholas.rutherford.habit.vibes.quote.repository.postgres

import com.nicholas.rutherford.habit.vibes.quote.Quotes
import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.QuoteRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class QuoteRepositoryImpl : QuoteRepository {
    override suspend fun getAllQuotes(): List<Quote> {
        return transaction {
            return@transaction Quotes.selectAll().map {
                Quote(
                    id = it[Quotes.id],
                    quoteText = it[Quotes.quoteText],
                    author = it[Quotes.author],
                    quoteSource = it[Quotes.quoteSource],
                    tags = it[Quotes.tags].split(", "),
                    createdAt = it[Quotes.createdAt],
                    loggedBy = it[Quotes.loggedBy],
                )
            }
        }
    }

    override suspend fun postQuote(quote: Quote) {
        transaction {
            Quotes.insert {
                it[quoteText] = quote.quoteText
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
                Quotes.insert {
                    it[quoteText] = quote.quoteText
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

            val quotes =
                Quotes
                    .selectAll() // todo -> Figure out a way we can just select what we need vs everything
                    .map {
                        Quote(
                            id = it[Quotes.id],
                            quoteText = it[Quotes.quoteText],
                            author = it[Quotes.author],
                            quoteSource = it[Quotes.quoteSource],
                            tags = it[Quotes.tags].split(", "),
                            createdAt = it[Quotes.createdAt],
                            loggedBy = it[Quotes.loggedBy],
                        )
                    }

            return@transaction quotes.firstOrNull { it.quoteText.lowercase() == cleanedTitle }
        }
    }

    override suspend fun getRandomQuote(): Quote? {
        return transaction {
            val quotes =
                Quotes
                    .selectAll() // todo -> Figure out a way we can just select what we need vs everything
                    .map {
                        Quote(
                            id = it[Quotes.id],
                            quoteText = it[Quotes.quoteText],
                            author = it[Quotes.author],
                            quoteSource = it[Quotes.quoteSource],
                            tags = it[Quotes.tags].split(", "),
                            createdAt = it[Quotes.createdAt],
                            loggedBy = it[Quotes.loggedBy],
                        )
                    }

            return@transaction if (quotes.isEmpty()) {
                null
            } else {
                quotes.random()
            }
        }
    }

    override suspend fun removeQuote(quote: Quote) {
        transaction {
            Quotes.deleteWhere { Quotes.id eq quote.id }
        }
    }
}
