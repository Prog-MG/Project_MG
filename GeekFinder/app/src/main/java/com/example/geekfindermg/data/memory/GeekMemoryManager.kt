package com.example.geekfindermg.data.memory

import com.example.geekfindermg.domain.repository.IGeekRepository
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.model.StoreFilter
import com.example.geekfindermg.domain.model.Review
import com.example.geekfindermg.domain.model.PriceLevel
import com.example.geekfindermg.domain.model.StoreCategory

class GeekMemoryManager: IGeekRepository {

    private val stores = mutableListOf<Store>()
    private val reviews = mutableListOf<Review>()
    private var nextStoreId = 1L
    private var nextReviewId = 1L


    //Store
    override fun getStores(): List<Store> {
        return stores.toList()
    }

    override fun getStoreById(id: Long): Store? {
        for (store in stores) {
            if (store.id == id) {
                return store
            }
        }
        return null
    }

    override fun searchStores(filter: StoreFilter): List<Store> {
        val result = mutableListOf<Store>()

        for (store in stores) {
            var matches = true

            // Texto de búsqueda
            if (filter.query.isNotBlank()) {
                if (!store.name.contains(filter.query.trim(), ignoreCase = true)) {
                    matches = false
                }
            }

            // Categoría
            if (filter.category != null) {
                if (store.category != filter.category) {
                    matches = false
                }
            }

            // Nivel de precio
            if (filter.priceLevel != null) {
                if (store.priceLevel != filter.priceLevel) {
                    matches = false
                }
            }

            // Calificación mínima
            if (store.averageRating < filter.minRating) {
                matches = false
            }

            if (matches) {
                result.add(store)
            }
        }
        return result
    }

    override fun addStore(store: Store): Store {
        val newStore = store.copy(id = nextStoreId, averageRating = 0.0)
        nextStoreId = nextStoreId + 1
        stores.add(newStore)
        return newStore
    }

    override fun updateStore(store: Store): Boolean {
        for (i in stores.indices) {
            if (stores[i].id == store.id) {
                stores[i] = store.copy(averageRating = stores[i].averageRating)
                return true
            }
        }
        return false
    }

    override fun deleteStore(id: Long): Boolean {
        val removed = stores.removeAll { it.id == id }
        if (removed) {
            reviews.removeAll { it.storeId == id }
        }
        return removed
    }

    //Reviews
    override fun getReviewsByStore(storeId: Long): List<Review> {
        val result = mutableListOf<Review>()
        for (review in reviews) {
            if (review.storeId == storeId) {
                result.add(review)
            }
        }
        return result.sortedByDescending { it.createdAt }
    }

    override fun addReview(review: Review): Review {
        val newReview = review.copy(id = nextReviewId)
        nextReviewId = nextReviewId + 1
        reviews.add(newReview)
        refreshAverageRating(newReview.storeId)
        return newReview
    }

    //Helpers
    private fun refreshAverageRating(storeId: Long) {
        val ratings = mutableListOf<Int>()
        for (review in reviews) {
            if (review.storeId == storeId) {
                ratings.add(review.rating)
            }
        }

        for (i in stores.indices) {
            if (stores[i].id == storeId) {
                stores[i] = stores[i].copy(averageRating = Rating.average(ratings))
            }
        }
    }
}