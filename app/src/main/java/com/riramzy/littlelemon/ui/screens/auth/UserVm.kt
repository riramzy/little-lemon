package com.riramzy.littlelemon.ui.screens.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.repos.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserVm @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState = _userState.asStateFlow()

    val isLoggedIn: StateFlow<Boolean> = userRepo.isLoggedInFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isOnboardingDone: StateFlow<Boolean> = userRepo.isOnboardingDoneFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val isReady: StateFlow<Boolean> = combine(
        userRepo.isLoggedInFlow,
        userRepo.isOnboardingDoneFlow
    ) { _, _ -> true }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val username: StateFlow<String?> = userRepo.usernameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val firstName: StateFlow<String?> = userRepo.firstNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val lastName: StateFlow<String?> = userRepo.lastNameFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val email: StateFlow<String?> = userRepo.emailFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val profilePicture: StateFlow<String?> = userRepo.profilePictureFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val fullName: StateFlow<String> =
        combine(userRepo.firstNameFlow, userRepo.lastNameFlow) { first, last ->
            "${first.orEmpty()} ${last.orEmpty()}".trim()
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun setOnboardingDone(onboardingDone: Boolean) {
        viewModelScope.launch {
            userRepo.setOnboardingDone(onboardingDone)
        }
    }

    fun register(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            if (_userState.value is UserState.Loading) return@launch
            _userState.value = UserState.Loading

            if (username.isBlank() || firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()) {
                _userState.value = UserState.Error("Please fill in all fields")
                return@launch
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _userState.value = UserState.Error("Please enter a valid email address")
                return@launch
            }

            if (password.length < 8) {
                _userState.value = UserState.Error("Password must be at least 8 characters long")
                return@launch
            }

            val result = userRepo.register(username, firstName, lastName, email, password)
            if (result.isSuccess) {
                _userState.value = UserState.Success
            } else {
                _userState.value =
                    UserState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            if (_userState.value is UserState.Loading) return@launch
            _userState.value = UserState.Loading

            if (email.isBlank() || password.isBlank()) {
                _userState.value = UserState.Error("Please fill in all fields")
                return@launch
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _userState.value = UserState.Error("Please enter a valid email address")
                return@launch
            }

            val result = userRepo.login(email, password)
            if (result.isSuccess) {
                _userState.value = UserState.Success
            } else {
                _userState.value =
                    UserState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepo.logout()
        }
    }

    fun deleteAccount(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                userRepo.deleteAccount()
                onSuccess()
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Failed to delete account")
            }
        }
    }

    fun editUsername(username: String) {
        viewModelScope.launch {
            try {
                userRepo.editUsername(username)
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Failed to edit username")
            }
        }
    }

    fun editFirstName(firstName: String) {
        viewModelScope.launch {
            try {
                userRepo.editFirstName(firstName)
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Failed to edit first name")
            }
        }
    }

    fun editLastName(lastName: String) {
        viewModelScope.launch {
            try {
                userRepo.editLastName(lastName)
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Failed to edit last name")
            }
        }
    }

    fun editEmail(email: String) {
        viewModelScope.launch {
            try {
                userRepo.editEmail(email)
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Failed to edit email")
            }
        }
    }

    fun saveProfilePicture(uri: String) {
        viewModelScope.launch {
            try {
                userRepo.saveProfilePicture(uri)
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Failed to save profile picture")
            }
        }
    }

    fun resetState() {
        _userState.value = UserState.Idle
    }
}