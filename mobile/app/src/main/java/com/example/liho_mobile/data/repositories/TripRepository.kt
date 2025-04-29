import android.util.Log
import com.example.liho_mobile.data.api.ApiService
import com.example.liho_mobile.data.models.Participant
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.data.models.TripWithDetails

class TripRepository(private val apiService: ApiService) {
    suspend fun getTrips(): Result<List<Trip>> {
        return try {
            val response = apiService.getTrips()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch trips data: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTripDetail(id: String): Result<TripWithDetails> {
        return try {
            val response = apiService.getTripDetail(id)
            if (response.isSuccessful) {
                Result.success(response.body() ?: TripWithDetails.default())
            } else {
                Result.failure(Exception("Failed to fetch trip data from id $id: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getTripParticipants(tripId: String): Result<List<Participant>> {
        return try {
            val response = apiService.getTripParticipants(tripId)
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Failed to fetch participants data from trip id $tripId: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}