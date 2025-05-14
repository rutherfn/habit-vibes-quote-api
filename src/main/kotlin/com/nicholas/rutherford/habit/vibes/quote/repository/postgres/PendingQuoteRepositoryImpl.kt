package com.nicholas.rutherford.habit.vibes.quote.repository.postgres

import com.nicholas.rutherford.habit.vibes.quote.PendingQuotes
import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.PendingQuoteRepository
import com.nicholas.rutherford.habit.vibes.quote.repository.QuoteRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PendingQuoteRepositoryImpl(
    private val quoteRepository: QuoteRepository,
) : PendingQuoteRepository {
    override suspend fun getAllPendingQuotes(): List<Quote> {
        return transaction {
            return@transaction PendingQuotes.selectAll().map {
                Quote(
                    id = it[PendingQuotes.id],
                    quoteText = it[PendingQuotes.quoteText],
                    author = it[PendingQuotes.author],
                    quoteSource = it[PendingQuotes.quoteSource],
                    tags = it[PendingQuotes.tags].split(", "),
                    createdAt = it[PendingQuotes.createdAt],
                    loggedBy = it[PendingQuotes.loggedBy],
                )
            }
        }
    }

    override suspend fun postQuote(quote: Quote) {
        transaction {
            PendingQuotes.insert {
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
                PendingQuotes.insert {
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
                PendingQuotes
                    .selectAll() // todo -> Figure out a way we can just select what we need vs everything
                    .map {
                        Quote(
                            id = it[PendingQuotes.id],
                            quoteText = it[PendingQuotes.quoteText],
                            author = it[PendingQuotes.author],
                            quoteSource = it[PendingQuotes.quoteSource],
                            tags = it[PendingQuotes.tags].split(", "),
                            createdAt = it[PendingQuotes.createdAt],
                            loggedBy = it[PendingQuotes.loggedBy],
                        )
                    }

            return@transaction quotes.firstOrNull { it.quoteText.lowercase() == cleanedTitle }
        }
    }

    override suspend fun removeQuote(quote: Quote) {
        transaction {
            PendingQuotes.deleteWhere { PendingQuotes.id eq quote.id }
        }
    }

    override suspend fun promoteQuote(quote: Quote) {
        if (!quoteRepository.getAllQuotes().contains(quote)) {
            quoteRepository.postQuote(quote = quote)
        }
    }

    override suspend fun promoteQuotes(quotes: List<Quote>) {
        val currentQuotes = quoteRepository.getAllQuotes()

        val quotesToPost = quotes.filterNot { it in currentQuotes }

        quotesToPost.forEach { quote ->
            quoteRepository.postQuote(quote = quote)
        }
    }
}
