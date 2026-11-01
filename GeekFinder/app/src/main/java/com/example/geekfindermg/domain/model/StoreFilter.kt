package com.example.geekfindermg.domain.model

data class StoreFilter(
    val query: String = "",
    val category: StoreCategory? = null,
    val priceLevel: PriceLevel? = null,
    val minRating: Int = 0
)
