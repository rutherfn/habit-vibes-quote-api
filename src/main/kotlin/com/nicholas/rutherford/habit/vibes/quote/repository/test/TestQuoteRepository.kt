package com.nicholas.rutherford.habit.vibes.quote.repository.test

import com.nicholas.rutherford.habit.vibes.quote.model.Quote
import com.nicholas.rutherford.habit.vibes.quote.repository.QuoteRepository

class TestQuoteRepository(overrideQuotes: MutableList<Quote>? = null) : QuoteRepository {
    private val quotes: MutableList<Quote> =
        overrideQuotes ?: mutableListOf(
            Quote(
                id = 1,
                quoteText = "The only limit to our realization of tomorrow is our doubts of today.",
                author = "Franklin D. Roosevelt",
                quoteSource = "Speech",
                tags = listOf("inspirational", "motivation", "future"),
                createdAt = "2025-04-27T10:00:00Z",
                loggedBy = "admin",
            ),
            Quote(
                id = 2,
                quoteText = "In the middle of every difficulty lies opportunity.",
                author = "Albert Einstein",
                quoteSource = "Interview",
                tags = listOf("inspirational", "challenge", "opportunity"),
                createdAt = "2025-04-27T10:05:00Z",
                loggedBy = "admin",
            ),
            Quote(
                id = 3,
                quoteText = "Success is not final, failure is not fatal: It is the courage to continue that counts.",
                author = "Winston Churchill",
                quoteSource = "Speech",
                tags = listOf("success", "failure", "perseverance"),
                createdAt = "2025-04-27T10:10:00Z",
                loggedBy = "user123",
            ),
            Quote(
                id = 4,
                quoteText = "Happiness is not something ready-made. It comes from your own actions.",
                author = "Dalai Lama",
                quoteSource = "Book",
                tags = listOf("happiness", "self-help", "life"),
                createdAt = "2025-04-27T10:15:00Z",
                loggedBy = "user456",
            ),
            Quote(
                id = 5,
                quoteText = "The future belongs to those who believe in the beauty of their dreams.",
                author = "Eleanor Roosevelt",
                quoteSource = "Speech",
                tags = listOf("dreams", "future", "inspiration"),
                createdAt = "2025-04-27T10:20:00Z",
                loggedBy = null,
            ),
            Quote(
                id = 6,
                quoteText = "It does not matter how slowly you go as long as you do not stop.",
                author = "Confucius",
                quoteSource = "Ancient Text",
                tags = listOf("perseverance", "patience", "motivation"),
                createdAt = "2025-04-27T10:25:00Z",
                loggedBy = "historian",
            ),
            Quote(
                id = 7,
                quoteText = "Believe you can and you're halfway there.",
                author = "Theodore Roosevelt",
                quoteSource = "Book",
                tags = listOf("belief", "motivation", "success"),
                createdAt = "2025-04-27T10:30:00Z",
                loggedBy = null,
            ),
            Quote(
                id = 8,
                quoteText = "Everything you’ve ever wanted is on the other side of fear.",
                author = "George Addair",
                quoteSource = "Lecture",
                tags = listOf("fear", "motivation", "growth"),
                createdAt = "2025-04-27T10:35:00Z",
                loggedBy = "guest",
            ),
            Quote(
                id = 9,
                quoteText = "Do what you can, with what you have, where you are.",
                author = "Theodore Roosevelt",
                quoteSource = "Speech",
                tags = listOf("resourcefulness", "action", "motivation"),
                createdAt = "2025-04-27T10:40:00Z",
                loggedBy = "user789",
            ),
            Quote(
                id = 10,
                quoteText = "Your time is limited, so don’t waste it living someone else’s life.",
                author = "Steve Jobs",
                quoteSource = "Commencement Speech",
                tags = listOf("life", "motivation", "individuality"),
                createdAt = "2025-04-27T10:45:00Z",
                loggedBy = "admin",
            ),
        )

    override suspend fun getQuoteByTitle(title: String): Quote? = quotes.find { it.quoteText.equals(title, ignoreCase = true) }

    override suspend fun getRandomQuote(): Quote? = quotes.random()

    override suspend fun getAllQuotes(): List<Quote> = quotes

    override suspend fun postQuote(quote: Quote) {
        if (getQuoteByTitle(title = quote.quoteText) != null) {
            throw IllegalStateException("Cannot create duplicate quote titles!")
        }
        quotes.add(quote)
    }

    override suspend fun postQuotes(quotesList: List<Quote>) {
        val quotesToAddArrayList: ArrayList<Quote> = arrayListOf()

        quotesList.forEach { quote ->
            if (getQuoteByTitle(title = quote.quoteText) == null) {
                quotesToAddArrayList.add(quote)
            } else {
                println("Cannot add quote $quote since it already exists")
            }
        }

        quotes.addAll(quotesToAddArrayList)
    }

    override suspend fun removeQuote(quote: Quote) {
        if (quotes.contains(quote)) {
            quotes.remove(quote)
        } else {
            println("Not able to remove quote since it currently does not exist")
        }
    }
}
