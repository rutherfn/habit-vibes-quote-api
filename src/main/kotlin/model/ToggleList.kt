package com.nciholas.rutherford.habit.vibes.quote.model

import kotlinx.serialization.Serializable

@Serializable
data class ToggleList(
    val toggles: List<Toggle>,
)
