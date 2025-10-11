package com.example.littlelemon.ui.viewmodel

import android.content.Context
import android.util.Patterns
import com.example.littlelemon.data.preferences.UserPreferences
import com.example.littlelemon.data.repos.UserRepo

class UserVm(context: Context) {
    private val userRepo = UserRepo(UserPreferences(context))

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
    ): Boolean {
        if (
            username.isBlank() || firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()
        ) {
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }

        if (password.length < 6) {
            return false
        }

        return userRepo.register(username, firstName, lastName, email, password)
    }

    fun login(username: String, password: String): Boolean {
        return userRepo.login(username, password)
    }

    fun logout() {
        userRepo.logout()
    }

    fun deleteAccount() {
        userRepo.deleteAccount()
    }

    fun getUsername(): String? { return userRepo.getUsername() }
    fun getFirstName(): String? { return userRepo.getFirstName() }
    fun getLastName(): String? { return userRepo.getLastName() }
    fun getEmail(): String? { return userRepo.getEmail() }

    fun isLoggedIn(): Boolean {
        return userRepo.isLoggedIn()
    }
}