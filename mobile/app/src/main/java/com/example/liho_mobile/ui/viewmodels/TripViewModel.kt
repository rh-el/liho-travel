package com.example.liho_mobile.ui.viewmodels


import TripRepository
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.liho_mobile.data.models.Participant
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.data.models.TripWithDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class TripsState {
    object Initial : TripsState()
    object Loading : TripsState()
    data class Success(val trips: List<Trip>) : TripsState()
    data class Error(val message: String) : TripsState()
}

sealed class TripDetailState {
    object Initial : TripDetailState()
    object Loading : TripDetailState()
    data class Success(val trip: TripWithDetails) : TripDetailState()
    data class Error(val message: String) : TripDetailState()
}

sealed class TripParticipantsState {
    object Initial : TripParticipantsState()
    object Loading : TripParticipantsState()
    data class Success(val participants: List<Participant>) : TripParticipantsState()
    data class Error(val message: String) : TripParticipantsState()
}

class TripViewModel(private val tripRepository: TripRepository) : ViewModel() {
    private val _tripsState = MutableStateFlow<TripsState>(TripsState.Initial)
    val tripsState : StateFlow<TripsState> = _tripsState.asStateFlow()

    private val _tripDetailState = MutableStateFlow<TripDetailState>(TripDetailState.Initial)
    val tripDetailState : StateFlow<TripDetailState> = _tripDetailState.asStateFlow()


    init {
        fetchTrips()
    }

    fun fetchTrips() {
        _tripsState.value = TripsState.Loading
        viewModelScope.launch {
            tripRepository.getTrips()
                .onSuccess { trips ->
                    _tripsState.value = TripsState.Success(trips)
                }
                .onFailure { error ->
                    _tripsState.value = TripsState.Error(error.message ?: "An error occurred while getting trips")
                }
        }
    }

    fun fetchTripDetail(id: String) {
        _tripDetailState.value = TripDetailState.Loading
        viewModelScope.launch {
            tripRepository.getTripDetail(id)
                .onSuccess { trip ->
                    _tripDetailState.value = TripDetailState.Success(trip)
                }
                .onFailure { error ->
                    _tripDetailState.value = TripDetailState.Error(error.message ?: "An error occurred while getting trip detail of id $id")
                }
        }
    }
}