package com.example.geekfindermg.Util

import kotlin.math.round; import kotlin.math.cos; import kotlin.math.pow; import kotlin.math.sin; import kotlin.math.sqrt; import kotlin.math.atan2

class util {

    object Rating {

        const val MIN_RATING = 1
        const val MAX_RATING = 5

        /** Average rounded to one decimal. Returns 0.0 when there are no ratings. */
        fun average(ratings: List<Int>): Double {
            if (ratings.isEmpty()) return 0.0
            return round(ratings.average() * 10) / 10.0
        }
    }

    object DistanceCalculator {

        private const val EARTH_RADIUS_KM = 6371.0

        /** Haversine distance between two coordinates, in kilometers. */
        fun distanceInKm(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
            val dLat = Math.toRadians(lat2 - lat1)
            val dLon = Math.toRadians(lon2 - lon1)
            val a = sin(dLat / 2).pow(2) +
                    cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
            return EARTH_RADIUS_KM * 2 * atan2(sqrt(a), sqrt(1 - a))
        }
    }

    sealed class State <out T> {
        object Loading : State<Nothing>()
        data class Success<T>(val data: T) : State<T>()
        data class Error(val message: String) : State<Nothing>()
    }

    object Validations {

        private const val PHONE_DIGITS = 8 // Costa Rica local number

        fun isValidName(name: String): Boolean = name.trim().length >= 2

        fun isValidPhone(phone: String): Boolean {
            val digits = phone.filter { it.isDigit() }
            val local = if (digits.startsWith("506") && digits.length == PHONE_DIGITS + 3) {
                digits.removePrefix("506")
            } else {
                digits
            }
            return local.length == PHONE_DIGITS
        }

        fun isValidOpeningHours(hours: String): Boolean = hours.isNotBlank()

        fun isValidRating(rating: Int): Boolean =
            rating in Rating.MIN_RATING..Rating.MAX_RATING

        fun isValidPrice(price: Double?): Boolean = price != null && price > 0.0

        fun isValidLocation(latitude: Double, longitude: Double): Boolean =
            latitude in -90.0..90.0 && longitude in -180.0..180.0 &&
                    !(latitude == 0.0 && longitude == 0.0)
    }

}