package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.domain.model.Review
import com.example.geekfindermg.domain.model.Store
import kotlinx.coroutines.flow.MutableStateFlow

class StoreDetailViewModel(private val repository: IGeekRepository) : ViewModel() {

    val store = MutableStateFlow<Store?>(null)
    val reviews = MutableStateFlow<List<Review>>(emptyList())
    val showDeleteDialog = MutableStateFlow(false)

    fun loadStore(storeId: Int) {
        store.value = repository.getStoreById(storeId)
        reviews.value = repository.getReviewsByStore(storeId)
    }

    fun showDelete() { showDeleteDialog.value = true }

    fun hideDelete() { showDeleteDialog.value = false }

    fun deleteStore() {
        store.value?.let { repository.deleteStore(it.id) }
        showDeleteDialog.value = false
    }
}