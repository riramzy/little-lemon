package com.example.littlelemon.data.repos

import com.example.littlelemon.data.preferences.UserPreferences

class UserRepo(
    private val userPreferences: UserPreferences,
) {
    fun login(username: String, password: String): Boolean {
        val isValid = userPreferences.validateLogin(username, password)
        if (isValid) {
            userPreferences.setLoggedIn(true)
        }
        return isValid
    }

    fun logout() {
        userPreferences.setLoggedIn(false)
    }

    fun deleteAccount() {
        userPreferences.clearAll()
        userPreferences.setLoggedIn(false)
    }

    fun isLoggedIn(): Boolean {
        return userPreferences.isLoggedIn()
    }


    fun isOnboardingDone(): Boolean {
        return userPreferences.isOnboardingDone()
    }

    fun setOnboardingDone(onboardingDone: Boolean) {
        userPreferences.setOnboardingDone(onboardingDone)
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

        userPreferences.saveUsername(username)
        userPreferences.register(username, firstName, lastName, email, password)
        userPreferences.setLoggedIn(true)
        return true
    }

    fun getFirstName(): String? = userPreferences.getFirstName()
    fun getLastName(): String? = userPreferences.getLastName()
    fun getEmail(): String? = userPreferences.getEmail()
    fun getUsername(): String? = userPreferences.getUsername()
    fun getFullName(): String? = userPreferences.getFullName()
}