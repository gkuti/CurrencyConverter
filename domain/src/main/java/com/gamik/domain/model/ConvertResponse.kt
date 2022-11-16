package com.gamik.domain.model

data class ConvertResponse(
    val date: String? = null,
    val result: Double,
    val success: Boolean? = null,
    val query: Query? = null,
    val info: Info? = null
)

data class Info(
    val rate: Double? = null,
    val timestamp: Int? = null
)

data class Query(
    val amount: Double? = null,
    val from: String? = null,
    val to: String? = null
)

