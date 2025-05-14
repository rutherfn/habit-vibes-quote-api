package com.nicholas.rutherford.habit.vibes.quote.model

import kotlinx.serialization.Serializable

@Serializable
data class Toggle(
    val id: Int,
    val enabled: Boolean,
    val name: String
)
