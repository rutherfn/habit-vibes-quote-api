package com.nciholas.rutherford.habit.vibes.quote.model

import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    val id: Int,
    val author: String,
    val source: String,
    val tags: List<String>,
    val createdAt: String,
    val loggedBy: String?
)