package com.example.geekfindermg.domain.model

data class Store(
    val id: Int,
    val name: String,
    val category: StoreCategory,
    val priceLevel: PriceLevel,
    val openingHours: String,
    val phone: String,
    val latitude: Double,
    val longitude: Double,
    val photoUri: String? = null,
    val averageRating: Double = 0.0
)
