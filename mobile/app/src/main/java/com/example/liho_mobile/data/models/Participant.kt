package com.example.liho_mobile.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Participant(
    val id: Int,
    @SerialName(value = "trip_id")
    val tripId: Int,
    @SerialName(value = "user_id")
    val userId: Int?,
    val username: String,
    @SerialName(value = "arrival_date")
    val arrivalDate: String,
    @SerialName(value = "departure_date")
    val departureDate: String
)
