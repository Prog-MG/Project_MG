package com.example.geekfindermg.domain.repository

import com.example.geekfindermg.domain.model.Review
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.model.StoreFilter


interface IGeekRepository {

    // Stores
    fun getStores(): List<Store>
    fun getStoreById(id: Long): Store?
    fun searchStores(filter: StoreFilter): List<Store>
    fun addStore(store: Store): Store
    fun updateStore(store: Store): Boolean
    fun deleteStore(id: Long): Boolean

    // Reviews
    fun getReviewsByStore(storeId: Long): List<Review>
    fun addReview(review: Review): Review

}