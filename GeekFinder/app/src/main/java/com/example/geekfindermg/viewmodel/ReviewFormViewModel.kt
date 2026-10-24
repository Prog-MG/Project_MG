package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.domain.model.Review
import kotlinx.coroutines.flow.MutableStateFlow

class ReviewFormViewModel(private val repository: IGeekRepository) : ViewModel() {
    val rating = MutableStateFlow(0)
    val comment = MutableStateFlow("")
    val pricePaid = MutableStateFlow("")
    val itemPhotoUri = MutableStateFlow<String?>(null)

    val ratingError = MutableStateFlow(false)
    val priceError = MutableStateFlow(false)

    fun save(storeId: Int, authorName: String): Boolean {
        val price = pricePaid.value.toDoubleOrNull() ?: 0.0
        ratingError.value = !Validations.isRatingValid(rating.value)
        priceError.value = !Validations.isPriceValid(price)
        if (ratingError.value || priceError.value) return false

        repository.addReview(
            Review(
                id = 0,
                storeId = storeId,
                authorName = authorName,
                rating = rating.value,
                comment = comment.value,
                pricePaid = price,
                itemPhotoUri = itemPhotoUri.value
            )
        )
        return true
    }
}
