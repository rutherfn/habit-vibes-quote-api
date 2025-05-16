package com.nicholas.rutherford.habit.vibes.quote.model

import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val id: Int,
    val quoteText: String,
    val author: String,
    val quoteSource: String,
    val tags: List<String>,
    val createdAt: String,
    val loggedBy: String?,
)
