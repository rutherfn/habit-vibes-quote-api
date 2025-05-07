package com.nciholas.rutherford.habit.vibes.quote.repository

import com.nciholas.rutherford.habit.vibes.quote.model.Quote

interface QuoteRepository {
    suspend fun getAllQuotes(): List<Quote>

    suspend fun postQuote(quote: Quote)

    suspend fun postQuotes(quotes: List<Quote>)

    suspend fun getQuoteByTitle(title: String): Quote?

    suspend fun removeQuote(quote: Quote)
}
