package com.riramzy.littlelemon.data.repos

import com.riramzy.littlelemon.data.preferences.UserPreferences

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

        userPreferences.editUsername(username)
        userPreferences.register(username, firstName, lastName, email, password)
        userPreferences.setLoggedIn(true)
        return true
    }

    fun getFirstName(): String? = userPreferences.getFirstName()

    fun editFirstName(firstName: String) {
        userPreferences.editFirstName(firstName)
    }

    fun getLastName(): String? = userPreferences.getLastName()

    fun editLastName(lastName: String) {
        userPreferences.editLastName(lastName)
    }

    fun getEmail(): String? = userPreferences.getEmail()

    fun editEmail(email: String) {
        userPreferences.editEmail(email)
    }

    fun getUsername(): String? = userPreferences.getUsername()

    fun editUsername(username: String) {
        userPreferences.editUsername(username)
    }

    fun getFullName(): String? = userPreferences.getFullName()

    fun saveProfilePicture(uri: String) {
        userPreferences.saveProfilePicture(uri)
    }

    fun getProfilePicture(): String? {
        return userPreferences.getProfilePicture()
    }
}