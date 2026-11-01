package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.Util.util
import com.example.geekfindermg.domain.model.PriceLevel
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.model.StoreCategory
import com.example.geekfindermg.domain.repository.IGeekRepository

class StoreFormViewModel(private val repository: IGeekRepository) : ViewModel() {

    var editingStoreId: Long? = null

    var name: String = ""
    var category: StoreCategory = StoreCategory.VIDEO_GAMES
    var priceLevel: PriceLevel = PriceLevel.MEDIUM
    var openingHours: String = ""
    var phone: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var photoUri: String? = null

    var nameError: Boolean = false
    var phoneError: Boolean = false

    fun loadStore(storeId: Long) {
        val store = repository.getStoreById(storeId) ?: return
        editingStoreId = store.id
        name = store.name
        category = store.category
        priceLevel = store.priceLevel
        openingHours = store.openingHours
        phone = store.phone
        latitude = store.latitude
        longitude = store.longitude
        photoUri = store.photoUri
    }

    fun save() {
        try {
            nameError = !util.Validations.isValidName(name)
            phoneError = !util.Validations.isValidName(phone)
            if (nameError || phoneError)
                throw Exception("The store name or phone is not valid.")

            val store = Store(
                id = editingStoreId ?: 0L,
                name = name,
                category = category,
                priceLevel = priceLevel,
                openingHours = openingHours,
                phone = phone,
                latitude = latitude,
                longitude = longitude,
                photoUri = photoUri
            )
            if (editingStoreId == null)
                repository.addStore(store)
            else
                repository.updateStore(store)
        } catch (e: Exception) {
            throw e
        }
    }
}