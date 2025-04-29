package com.example.liho_mobile.ui.viewmodels

import Accommodation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liho_mobile.data.api.ApiService
import com.example.liho_mobile.data.repositories.AccommodationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class LikeState {
    object Initial : LikeState()
    object Loading : LikeState()
    data class Success(val accommodation: Accommodation) : LikeState()
    data class Error(val message: String) : LikeState()
}

class AccommodationViewModel(private val accommodationRepository: AccommodationRepository) : ViewModel() {
    private val _likeState = MutableStateFlow<LikeState>(LikeState.Initial)
    val likeState : StateFlow<LikeState> = _likeState.asStateFlow()

    fun toggleLike(accommodation: Accommodation) {
        _likeState.value = LikeState.Loading
        viewModelScope.launch {
            accommodationRepository.toggleLike(accommodation.id.toString())
                .onSuccess {
                    val newAcc = accommodation.copy(isLikedByUser = !accommodation.isLikedByUser)
                    _likeState.value = LikeState.Success(newAcc)
                }
                .onFailure { error ->
                    _likeState.value = LikeState.Error(error.message ?: "An error occurred while liking accommodation id ${accommodation.id}")
                }
        }
    }
}