package com.example.liho_mobile.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val username: String,
    @SerialName(value = "first_name")
    val firstName: String,
    @SerialName(value = "last_name")
    val lastName: String,
    val email: String,
    val password: String,
    @SerialName(value = "is_admin")
    val isAdmin: Boolean = false,
    @SerialName(value = "created_at")
    val createdAt: String? = null,
    @SerialName(value = "updated_at")
    val updatedAt: String? = null
)

