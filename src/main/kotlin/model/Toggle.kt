package com.nciholas.rutherford.habit.vibes.quote.model

import kotlinx.serialization.Serializable

@Serializable
data class Toggle(
    val id: Int,
    val toggleEnabledState: Int,
    val name: String
)