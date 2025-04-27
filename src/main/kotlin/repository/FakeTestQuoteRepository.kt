package com.nciholas.rutherford.habit.vibes.quote.repository

import com.nciholas.rutherford.habit.vibes.quote.model.Quote

object FakeTestQuoteRepository {
    private val quotes: MutableList<Quote> = mutableListOf()

    fun allQuotes(): List<Quote> = quotes

    fun quoteByTitle(title: String) = quotes.find {
        it.title.equals(title, ignoreCase = true)
    }

    fun addQuote(quote: Quote) {
        if (quoteByTitle(title = quote.title) != null) {
            throw IllegalStateException("Cannot duplicate task names!")
        }
        quotes.add(quote)
    }
}