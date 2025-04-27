package com.nciholas.rutherford.habit.vibes.quote.repository

import com.nciholas.rutherford.habit.vibes.quote.model.Quote

interface QuoteRepository {
    suspend fun allAllQuotes(): List<Quote>
    suspend fun addQuote(quote: Quote)
    suspend fun quoteByTitle(title: String): Quote?
    suspend fun removeQuote(quote: Quote)
}