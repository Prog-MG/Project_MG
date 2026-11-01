package com.example.geekfindermg.domain.model

data class Review(
    val id: Long,
    val storeId: Long,
    val authorName: String,
    val rating: Int,
    val comment: String,
    val pricePaid: Double,
    val itemPhotoUri: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)
