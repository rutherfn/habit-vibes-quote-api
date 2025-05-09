package com.nicholas.rutherford.habit.vibes.quote

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(val error: String, val details: String? = null)
