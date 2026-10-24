package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.domain.model.PriceLevel
import com.example.geekfindermg.domain.model.Store
import com.example.geekfindermg.domain.model.StoreCategory
import kotlinx.coroutines.flow.MutableStateFlow

class StoreFormViewModel(private val repository: IGeekRepository) : ViewModel(){
    var editingStoreId: Int? = null

    val name = MutableStateFlow("")
    val category = MutableStateFlow(StoreCategory.VIDEO_GAMES)
    val priceLevel = MutableStateFlow(PriceLevel.MEDIUM)
    val openingHours = MutableStateFlow("")
    val phone = MutableStateFlow("")
    val latitude = MutableStateFlow(0.0)
    val longitude = MutableStateFlow(0.0)
    val photoUri = MutableStateFlow<String?>(null)

    val nameError = MutableStateFlow(false)
    val phoneError = MutableStateFlow(false)

    fun loadStore(storeId: Int) {
        val store = repository.getStoreById(storeId) ?: return
        editingStoreId = store.id
        name.value = store.name
        category.value = store.category
        priceLevel.value = store.priceLevel
        openingHours.value = store.openingHours
        phone.value = store.phone
        latitude.value = store.latitude
        longitude.value = store.longitude
        photoUri.value = store.photoUri
    }

    fun save(): Boolean {
        nameError.value = !Validations.isNameValid(name.value)
        phoneError.value = !Validations.isPhoneValid(phone.value)
        if (nameError.value || phoneError.value) return false

        val store = Store(
            id = editingStoreId ?: 0,
            name = name.value,
            category = category.value,
            priceLevel = priceLevel.value,
            openingHours = openingHours.value,
            phone = phone.value,
            latitude = latitude.value,
            longitude = longitude.value,
            photoUri = photoUri.value
        )
        if (editingStoreId == null) repository.addStore(store) else repository.updateStore(store)
        return true
    }
}