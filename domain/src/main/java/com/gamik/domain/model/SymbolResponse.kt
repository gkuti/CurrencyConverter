package com.gamik.domain.model

data class SymbolResponse(
    val success: Boolean,
    val symbols: Map<String, String>
)