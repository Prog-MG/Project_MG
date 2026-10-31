package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.domain.model.Review
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.repository.IGeekRepository

class StoreDetailViewModel(private val repository: IGeekRepository) : ViewModel() {

    var store: Store? = null
    var reviews: List<Review> = emptyList()
    var showDeleteDialog: Boolean = false

    fun loadStore(storeId: Long) {
        store = repository.getStoreById(storeId)
        reviews = repository.getReviewsByStore(storeId)
    }

    fun showDelete() {
        showDeleteDialog = true
    }

    fun hideDelete() {
        showDeleteDialog = false
    }

    fun deleteStore() {
        try {
            val currentStore = store
            if (currentStore != null)
                repository.deleteStore(currentStore.id)
            else
                throw Exception("It's not possible to delete the store " +
                        "because it does not exist.")
            showDeleteDialog = false
        } catch (e: Exception) {
            throw e
        }
    }
}