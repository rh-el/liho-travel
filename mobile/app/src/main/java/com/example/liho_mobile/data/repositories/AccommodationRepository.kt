package com.example.liho_mobile.data.repositories

import Accommodation
import com.example.liho_mobile.data.api.ApiService

class AccommodationRepository(private val apiService: ApiService) {

    suspend fun toggleLike(accommodationId: String): Result<Unit> {
        return try {
            val response = apiService.likeAccommodation(accommodationId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to like or dislike accommodation of id $accommodationId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}