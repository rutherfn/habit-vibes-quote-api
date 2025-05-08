package com.nciholas.rutherford.habit.vibes.quote.repository

import com.nciholas.rutherford.habit.vibes.quote.model.Quote

interface PendingQuoteRepository {
    suspend fun getAllPendingQuotes(): List<Quote>

    suspend fun postQuote(quote: Quote)

    suspend fun postQuotes(quotes: List<Quote>)

    suspend fun getQuoteByTitle(title: String): Quote?

    suspend fun removeQuote(quote: Quote)

    suspend fun promoteQuote(quote: Quote)

    suspend fun promoteQuotes(quotes: List<Quote>)
}
