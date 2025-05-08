package com.nciholas.rutherford.habit.vibes.quote.repository.test

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import com.nciholas.rutherford.habit.vibes.quote.repository.PendingQuoteRepository
import com.nciholas.rutherford.habit.vibes.quote.repository.QuoteRepository

class TestPendingQuoteRepository(
    overrideQuotes: MutableList<Quote>? = null,
    private val quoteRepository: QuoteRepository,
) : PendingQuoteRepository {
    private val quotes: MutableList<Quote> =
        overrideQuotes ?: mutableListOf(
            Quote(
                id = 1,
                title = "Great things are not done by impulse, but by a series of small things brought together.",
                author = "Vincent Van Gogh",
                source = "Letter",
                tags = listOf("patience", "creativity", "effort"),
                createdAt = "2025-04-27T10:00:00Z",
                loggedBy = "admin",
            ),
            Quote(
                id = 2,
                title = "Don't watch the clock; do what it does. Keep going.",
                author = "Sam Levenson",
                source = "Book",
                tags = listOf("time", "motivation", "persistence"),
                createdAt = "2025-04-27T10:05:00Z",
                loggedBy = "admin",
            ),
            Quote(
                id = 3,
                title = "What lies behind us and what lies before us are tiny matters compared to what lies within us.",
                author = "Ralph Waldo Emerson",
                source = "Essay",
                tags = listOf("strength", "character", "potential"),
                createdAt = "2025-04-27T10:10:00Z",
                loggedBy = "user123",
            ),
            Quote(
                id = 4,
                title = "Act as if what you do makes a difference. It does.",
                author = "William James",
                source = "Lecture",
                tags = listOf("impact", "life", "action"),
                createdAt = "2025-04-27T10:15:00Z",
                loggedBy = "user456",
            ),
        )

    override suspend fun getQuoteByTitle(title: String): Quote? = quotes.find { it.title.equals(title, ignoreCase = true) }

    override suspend fun getAllPendingQuotes(): List<Quote> = quotes

    override suspend fun postQuote(quote: Quote) {
        if (getQuoteByTitle(title = quote.title) != null) {
            throw IllegalStateException("Cannot duplicate pending quotes names!")
        }
        quotes.add(quote)
    }

    override suspend fun postQuotes(quotesList: List<Quote>) {
        val quotesToAddArrayList: ArrayList<Quote> = arrayListOf()

        quotesList.forEach { quote ->
            if (getQuoteByTitle(title = quote.title) == null) {
                quotesToAddArrayList.add(quote)
            } else {
                println("Cannot add pending quote $quote since it already exists")
            }
        }

        quotes.addAll(quotesToAddArrayList)
    }

    override suspend fun removeQuote(quote: Quote) {
        if (quotes.contains(quote)) {
            quotes.remove(quote)
        } else {
            println("Not able to remove pending quote since it currently does not exist")
        }
    }

    override suspend fun promoteQuote(quote: Quote) {
        val allQuotes = quoteRepository.getAllQuotes()

        if (allQuotes.contains(quote)) {
            println("Cannot promote pending quote to actual quote - $quote since quote already exists")
        } else {
            quoteRepository.postQuote(quote = quote)
            removeQuote(quote = quote)
        }
    }

    override suspend fun promoteQuotes(quotes: List<Quote>) {
        val allQuotes = quoteRepository.getAllQuotes()

        quotes.forEach { quote ->
            if (allQuotes.contains(quote)) {
                println("Cannot promote pending quote to actual quote - $quote since quote already exists")
            } else {
                quoteRepository.postQuote(quote = quote)
                removeQuote(quote = quote)
            }
        }
    }
}
