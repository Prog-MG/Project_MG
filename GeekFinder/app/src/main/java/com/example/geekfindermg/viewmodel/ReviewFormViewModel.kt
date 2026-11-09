package com.example.geekfindermg.viewmodel

import androidx.lifecycle.ViewModel
import com.example.geekfindermg.Util.util
import com.example.geekfindermg.domain.model.Review
import com.example.geekfindermg.domain.repository.IGeekRepository

class ReviewFormViewModel(private val repository: IGeekRepository) : ViewModel() {

    var rating: Int = 0
    var comment: String = ""
    var pricePaid: String = ""
    var itemPhotoUri: String? = null

    var ratingError: Boolean = false
    var priceError: Boolean = false

    fun save(storeId: Long, authorName: String) {
        try {
            val price = pricePaid.toDoubleOrNull() ?: 0.0
            ratingError = !util.Validations.isValidRating(rating)
            priceError = !util.Validations.isValidRating(price.toInt())
            if (ratingError || priceError)
                throw Exception("The rating or the price paid is not valid.")

            repository.addReview(
                Review(
                    id = 0L,
                    storeId = storeId,
                    authorName = authorName,
                    rating = rating,
                    comment = comment,
                    pricePaid = price,
                    itemPhotoUri = itemPhotoUri
                )
            )
        } catch (e: Exception) {
            throw e
        }
    }
}
