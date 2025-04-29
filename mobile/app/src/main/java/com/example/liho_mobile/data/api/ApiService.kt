package com.example.liho_mobile.data.api

import Accommodation
import com.example.liho_mobile.data.models.Participant
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.data.models.TripWithDetails
import com.example.liho_mobile.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

    @POST("users")
    suspend fun createUser(@Body user: User): Response<User>

    @GET("trips/{id}")
    suspend fun getTripDetail(@Path("id") id: String): Response<TripWithDetails>

    @GET("trips")
    suspend fun getTrips(): Response<List<Trip>>

    @GET("participants/{tripId}")
    suspend fun getTripParticipants(@Path("tripId") tripId: String): Response<List<Participant>>

    @POST("accommodation/{accommodationId}/like")
    suspend fun likeAccommodation(@Path("accommodationId") accommodationId: String): Response<Unit>

}