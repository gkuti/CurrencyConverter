package com.gamik.domain.model

data class HistoryResponse(
    val timeseries: Boolean? = null,
    val success: Boolean? = null,
    val rates: Map<String, Map<String, String>>
)