package com.nicholas.rutherford.habit.vibes.quote.repository.postgres

import com.nicholas.rutherford.habit.vibes.quote.Quotes
import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.QuoteRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.text.split

class QuoteRepositoryImpl : QuoteRepository {
    override suspend fun getAllQuotes(): List<Quote> {
        return transaction {
            return@transaction Quotes.selectAll().map {
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
        }
    }

    override suspend fun postQuote(quote: Quote) {
        transaction {
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

    override suspend fun postQuotes(quotes: List<Quote>) {
        transaction {
            quotes.forEach { quote ->
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

    override suspend fun getQuoteByTitle(title: String): Quote? {
        return transaction {
            val quoteRow = Quotes.select( Quotes.text.eq(title)).singleOrNull()

            return@transaction quoteRow?.let {
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
        }
    }





    override suspend fun removeQuote(quote: Quote) {
        transaction {
            Quotes.deleteWhere { Quotes.id eq quote.id } // Deletes the quote by its id
        }
    }
}