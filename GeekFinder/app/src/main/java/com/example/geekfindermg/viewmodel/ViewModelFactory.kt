package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geekfindermg.domain.repository.IGeekRepository

class ViewModelFactory (private val repository: IGeekRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            StoreListViewModel::class.java -> StoreListViewModel(repository)
            StoreDetailViewModel::class.java -> StoreDetailViewModel(repository)
            StoreFormViewModel::class.java -> StoreFormViewModel(repository)
            ReviewFormViewModel::class.java -> ReviewFormViewModel(repository)
            else -> throw IllegalArgumentException("Unknown ViewModel")
        } as T
    }
}