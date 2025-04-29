package com.example.liho_mobile.data.models.reponse

data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null
)