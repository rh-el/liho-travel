package com.example.liho_mobile.data.models

import Accommodation
import androidx.collection.ObjectList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Collections
import java.util.Objects

@Serializable
data class TripWithDetails(
    val id: Int,
    val name: String,
    @SerialName(value = "created_by")
    val createdBy: Int,
    @SerialName(value = "nb_participants")
    val nbParticipants: Int,
    val destination: String,
    @SerialName(value = "start_date")
    val startDate: String,
    @SerialName(value = "end_date")
    val endDate: String,
    val description: String,
    @SerialName(value = "created_at")
    val createdAt: String,
    @SerialName(value = "updated_at")
    val updatedAt: String?,
    val participants: List<Participant>,
    val accommodation: List<Accommodation>,
) {
    companion object {
        fun default() = TripWithDetails(
            id = -1,
            name = "Unknown",
            createdBy = -1,
            nbParticipants = -1,
            destination = "Unknown",
            startDate = "Unknown",
            endDate = "Unknown",
            description = "Unknown",
            createdAt = "Unknown",
            updatedAt = "Unknown",
            participants = emptyList(),
            accommodation = emptyList(),
        )
    }
}
