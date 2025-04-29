package com.example.liho_mobile.data.repositories

import com.example.liho_mobile.data.api.ApiService
import com.example.liho_mobile.data.models.User

class UserRepository(private val apiService: ApiService) {
    suspend fun getUsers(): Result<List<User>> {
        return try {
            val response = apiService.getUsers()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch users data: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createUser(user: User): Result<User> {
        return try {
            val response = apiService.createUser(user)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Failed to create user"))
            } else {
                Result.failure(Exception("Failed to create user: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}