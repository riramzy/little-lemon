package com.example.littlelemon.ui.viewmodel

import android.util.Patterns
import com.example.littlelemon.data.repos.UserRepo

class UserVm(
    private val userRepo: UserRepo
) {
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

    fun editUsername(username: String) {
        userRepo.editUsername(username)
    }

    fun getFirstName(): String? { return userRepo.getFirstName() }

    fun editFirstName(firstName: String) {
        userRepo.editFirstName(firstName)
    }

    fun getLastName(): String? { return userRepo.getLastName() }

    fun editLastName(lastName: String) {
        userRepo.editLastName(lastName)
    }

    fun getFullName(): String? { return userRepo.getFullName() }
    fun getEmail(): String? { return userRepo.getEmail() }

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