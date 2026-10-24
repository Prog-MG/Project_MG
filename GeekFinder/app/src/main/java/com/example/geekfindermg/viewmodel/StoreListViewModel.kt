package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.model.StoreFilter
import com.example.geekfindermg.domain.model.ViewMode
import kotlinx.coroutines.flow.MutableStateFlow

class StoreListViewModel(private val repository: IGeekRepository) : ViewModel() {
    val stores = MutableStateFlow<List<Store>>(emptyList())
    val filter = MutableStateFlow(StoreFilter())
    val viewMode = MutableStateFlow(ViewMode.MAP)
    val selectedStore = MutableStateFlow<Store?>(null)

    init { loadStores() }

    fun loadStores() {
        stores.value = repository.searchStores(filter.value)
    }

    fun updateFilter(newFilter: StoreFilter) {
        filter.value = newFilter
        loadStores()
    }

    fun setViewMode(mode: ViewMode) { viewMode.value = mode }

    fun selectStore(store: Store?) { selectedStore.value = store }

}