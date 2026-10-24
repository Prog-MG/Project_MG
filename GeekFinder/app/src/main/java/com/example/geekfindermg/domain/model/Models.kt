package com.example.geekfindermg.domain.model

// REFERENCE ONLY: adjust to match the models you define yourself.

enum class StoreCategory { VIDEO_GAMES, FIGURES, CARDS }

enum class PriceLevel { LOW, MEDIUM, HIGH }

data class Store(
    val id: Long = 0L,
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

data class Review(
    val id: Long = 0L,
    val storeId: Long,
    val authorName: String,
    val rating: Int,
    val comment: String,
    val pricePaid: Double,
    val itemPhotoUri: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)

data class StoreFilter(
    val query: String = "",
    val category: StoreCategory? = null,
    val priceLevel: PriceLevel? = null,
    val minRating: Double = 0.0
)