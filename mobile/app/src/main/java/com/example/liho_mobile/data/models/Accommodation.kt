import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.Serial

@Serializable
data class Accommodation(
    val id: Int,
    @SerialName(value = "trip_id")
    val tripId: Int,
    val url: String?,
    @SerialName(value = "additional_information")
    val additionalInformation: String?,
    @SerialName(value = "is_booked")
    val isBooked: Boolean,
    val price: Int,
    val name: String,
    val address: String,
    val capacity: Int,
    @SerialName(value = "is_liked_by")
    val isLikedBy: List<Int>,
    @SerialName(value = "is_liked_by_user")
    val isLikedByUser: Boolean,
    @SerialName(value = "created_at")
    val createdAt: String,
    @SerialName(value = "updated_at")
    val updatedAt: String?,
) {
    companion object {
        fun default() = Accommodation(
            id = -1,
            tripId = 3,
            url = "https://www.airbnb.fr/rooms/45538743?adults=1&check_in=2025-07-19&check_out=2025-07-26&search_mode=regular_search&source_impression_id=p3_1742983133_P3447FRYfIACPzLC&previous_page_section_name=1000&federated_search_id=351af031-6f23-4b2a-83a4-69b614b0ec8a",
            additionalInformation = "some infos",
            isBooked = false,
            price = 1842,
            name = "Castell de Sant Carles",
            address = "Poniente, 07015 Palma, Balearic Islands",
            capacity = 12,
            createdAt = "Unknown",
            updatedAt = "Unknown",
            isLikedBy = listOf(12,15,2),
            isLikedByUser = false
        )
    }
}
