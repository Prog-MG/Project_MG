package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.model.StoreFilter
import com.example.geekfindermg.domain.model.ViewMode
import com.example.geekfindermg.domain.repository.IGeekRepository

class StoreListViewModel(private val repository: IGeekRepository) : ViewModel() {

    var stores: List<Store> = emptyList()
    var filter: StoreFilter = StoreFilter()
    var viewMode: ViewMode = ViewMode.MAP
    var selectedStore: Store? = null

    init {
        loadStores()
    }

    fun loadStores() {
        stores = repository.searchStores(filter)
    }

    fun updateFilter(newFilter: StoreFilter) {
        filter = newFilter
        loadStores()
    }

    fun setViewMode(mode: ViewMode) {
        viewMode = mode
    }

    fun selectStore(store: Store?) {
        selectedStore = store
    }
}