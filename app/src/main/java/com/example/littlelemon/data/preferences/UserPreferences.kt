package com.example.littlelemon.data.preferences

import android.content.Context

class UserPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_ONBOARDING_DONE = "onboarding_done"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_FIRST_NAME = "first_name"
        private const val KEY_LAST_NAME = "last_name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PROFILE_PICTURE = "profile_picture"
    }

    fun getProfilePicture(): String? {
        return sharedPreferences.getString(KEY_PROFILE_PICTURE, null)
    }

    fun saveProfilePicture(uri: String) {
        sharedPreferences.edit().putString(KEY_PROFILE_PICTURE, uri).apply()
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun register(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ) {
        sharedPreferences.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_FIRST_NAME, firstName)
            .putString(KEY_LAST_NAME, lastName)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PASSWORD, password)
            .apply()
    }

    fun validateLogin(username: String, password: String): Boolean {
        val savedEmail = sharedPreferences.getString(KEY_USERNAME, null)
        val savedPassword = sharedPreferences.getString(KEY_PASSWORD, null)
        return username == savedEmail && password == savedPassword
    }

    fun getFirstName(): String? = sharedPreferences.getString(KEY_FIRST_NAME, null)

    fun editFirstName(firstName: String) {
        sharedPreferences.edit().putString(KEY_FIRST_NAME, firstName).apply()
    }
    fun getLastName(): String? = sharedPreferences.getString(KEY_LAST_NAME, null)

    fun editLastName(lastName: String) {
        sharedPreferences.edit().putString(KEY_LAST_NAME, lastName).apply()
    }
    fun getFullName(): String? = "${getFirstName()} ${getLastName()}"
    fun getEmail(): String? = sharedPreferences.getString(KEY_EMAIL, null)

    fun editEmail(email: String) {
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply()
    }
    fun getUsername(): String? = sharedPreferences.getString(KEY_USERNAME, null)

    fun editUsername(username: String) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply()
    }

    fun setOnboardingDone(onboardingDone: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ONBOARDING_DONE, onboardingDone).apply()
    }

    fun isOnboardingDone(): Boolean {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_DONE, false)
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}