package com.nciholas.rutherford.habit.vibes.quote.repository.test

import com.nciholas.rutherford.habit.vibes.quote.model.Quote
import com.nciholas.rutherford.habit.vibes.quote.repository.PendingQuoteRepository
import com.nciholas.rutherford.habit.vibes.quote.repository.QuoteRepository

class TestPendingQuoteRepository(
    overrideQuotes: MutableList<Quote>? = null,
    private val quoteRepository: QuoteRepository
): PendingQuoteRepository {
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
            Quote(
                id = 5,
                title = "The best way to predict your future is to create it.",
                author = "Abraham Lincoln",
                source = "Speech",
                tags = listOf("future", "self-determination", "dreams"),
                createdAt = "2025-04-27T10:20:00Z",
                loggedBy = null,
            ),
            Quote(
                id = 6,
                title = "A journey of a thousand miles begins with a single step.",
                author = "Lao Tzu",
                source = "Ancient Text",
                tags = listOf("journey", "beginning", "perseverance"),
                createdAt = "2025-04-27T10:25:00Z",
                loggedBy = "historian",
            ),
            Quote(
                id = 7,
                title = "If you want to lift yourself up, lift up someone else.",
                author = "Booker T. Washington",
                source = "Speech",
                tags = listOf("kindness", "uplift", "motivation"),
                createdAt = "2025-04-27T10:30:00Z",
                loggedBy = null,
            ),
            Quote(
                id = 8,
                title = "Courage doesn’t always roar. Sometimes courage is the quiet voice at the end of the day saying, ‘I will try again tomorrow.’",
                author = "Mary Anne Radmacher",
                source = "Essay",
                tags = listOf("courage", "resilience", "hope"),
                createdAt = "2025-04-27T10:35:00Z",
                loggedBy = "guest",
            ),
            Quote(
                id = 9,
                title = "Opportunities don't happen. You create them.",
                author = "Chris Grosser",
                source = "Interview",
                tags = listOf("opportunity", "action", "motivation"),
                createdAt = "2025-04-27T10:40:00Z",
                loggedBy = "user789",
            ),
            Quote(
                id = 10,
                title = "Don’t wait. The time will never be just right.",
                author = "Napoleon Hill",
                source = "Book",
                tags = listOf("action", "motivation", "time"),
                createdAt = "2025-04-27T10:45:00Z",
                loggedBy = "admin",
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
        }
    }

    override suspend fun promoteQuotes(quotes: List<Quote>) {
        val allQuotes = quoteRepository.getAllQuotes()

        quotes.forEach { quote ->
            if (allQuotes.contains(quote)) {
                println("Cannot promote pending quote to actual quote - $quote since quote already exists")
            } else {
                quoteRepository.postQuote(quote = quote)
            }
        }
    }

}