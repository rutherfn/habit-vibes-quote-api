package com.nciholas.rutherford.habit.vibes.quote

@kotlinx.serialization.Serializable
data class ErrorResponse(val error: String, val details: String? = null)
