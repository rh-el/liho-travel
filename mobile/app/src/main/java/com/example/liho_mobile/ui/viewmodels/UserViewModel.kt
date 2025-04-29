package com.example.liho_mobile.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.liho_mobile.data.models.Trip
import com.example.liho_mobile.data.models.User
import com.example.liho_mobile.data.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UserState {
    object Initial : UserState()
    object Loading : UserState()
    data class Success(val user: List<User>) : UserState()
    data class Error(val message: String) : UserState()
}

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    val userState: StateFlow<UserState> = _userState.asStateFlow()

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            userRepository.getUsers()
                .onSuccess { users ->
                    _userState.value = UserState.Success(users)
                }
                .onFailure { error ->
                    _userState.value = UserState.Error(error.message ?: "An error occurred while getting users")
                }
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            userRepository.createUser(user)
                .onSuccess { _ ->
                    fetchUsers()
                }
                .onFailure { _ ->
                    Log.d("createUser error", "an error occurred while creating user")
                }
        }
    }
}

