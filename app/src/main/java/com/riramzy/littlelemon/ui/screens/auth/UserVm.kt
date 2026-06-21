package com.riramzy.littlelemon.ui.screens.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riramzy.littlelemon.data.repos.UserRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserVm @Inject constructor(
    private val userRepo: UserRepo
) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState = _userState.asStateFlow()
    fun setOnboardingDone(onboardingDone: Boolean) {
        userRepo.setOnboardingDone(onboardingDone)
    }

    fun isOnboardingDone(): Boolean {
        return userRepo.isOnboardingDone()
    }

    fun register(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
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
                return@launch
            } else {
                _userState.value =
                    UserState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
                return@launch
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            val result = userRepo.login(username, password)
            if (result.isSuccess) {
                _userState.value = UserState.Success
            } else {
                _userState.value =
                    UserState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        userRepo.logout()
    }

    fun deleteAccount() {
        userRepo.deleteAccount()
    }

    fun getUsername(): String? {
        return userRepo.getUsername()
    }

    fun editUsername(username: String) {
        userRepo.editUsername(username)
    }

    fun getFirstName(): String? {
        return userRepo.getFirstName()
    }

    fun editFirstName(firstName: String) {
        userRepo.editFirstName(firstName)
    }

    fun getLastName(): String? {
        return userRepo.getLastName()
    }

    fun editLastName(lastName: String) {
        userRepo.editLastName(lastName)
    }

    fun getFullName(): String {
        return userRepo.getFullName()
    }

    fun getEmail(): String? {
        return userRepo.getEmail()
    }

    fun editEmail(email: String) {
        userRepo.editEmail(email)
    }


    fun isLoggedIn(): Boolean {
        return userRepo.isLoggedIn()
    }

    fun saveProfilePicture(uri: String) {
        userRepo.saveProfilePicture(uri)
    }

    fun getProfilePicture(): String? {
        return userRepo.getProfilePicture()
    }
}