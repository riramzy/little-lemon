package com.riramzy.littlelemon.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferences(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_ONBOARDING_DONE = booleanPreferencesKey("onboarding_done")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_FIRST_NAME = stringPreferencesKey("first_name")
        private val KEY_LAST_NAME = stringPreferencesKey("last_name")
        private val KEY_EMAIL = stringPreferencesKey("email")
        private val KEY_PROFILE_PICTURE = stringPreferencesKey("profile_picture")
    }

    val isLoggedInFlow: Flow<Boolean> = dataStore.data.map { it[KEY_IS_LOGGED_IN] ?: false }
    val isOnboardingDoneFlow: Flow<Boolean> =
        dataStore.data.map { it[KEY_ONBOARDING_DONE] ?: false }
    val usernameFlow: Flow<String?> = dataStore.data.map { it[KEY_USERNAME] }
    val firstNameFlow: Flow<String?> = dataStore.data.map { it[KEY_FIRST_NAME] }
    val lastNameFlow: Flow<String?> = dataStore.data.map { it[KEY_LAST_NAME] }
    val emailFlow: Flow<String?> = dataStore.data.map { it[KEY_EMAIL] }
    val profilePictureFlow: Flow<String?> = dataStore.data.map { it[KEY_PROFILE_PICTURE] }

    suspend fun getProfilePicture(): String? =
        dataStore.data.map { it[KEY_PROFILE_PICTURE] }.first()

    suspend fun saveProfilePicture(uri: String) {
        dataStore.edit { it[KEY_PROFILE_PICTURE] = uri }
    }

    suspend fun setLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { it[KEY_IS_LOGGED_IN] = isLoggedIn }
    }

    suspend fun isLoggedIn(): Boolean = dataStore.data.map { it[KEY_IS_LOGGED_IN] ?: false }.first()

    suspend fun register(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
    ) {
        dataStore.edit {
            it[KEY_USERNAME] = username
            it[KEY_FIRST_NAME] = firstName
            it[KEY_LAST_NAME] = lastName
            it[KEY_EMAIL] = email
        }
    }

    suspend fun getFirstName(): String? = dataStore.data.map { it[KEY_FIRST_NAME] }.first()

    suspend fun editFirstName(firstName: String) {
        dataStore.edit { it[KEY_FIRST_NAME] = firstName }
    }

    suspend fun getLastName(): String? = dataStore.data.map { it[KEY_LAST_NAME] }.first()

    suspend fun editLastName(lastName: String) {
        dataStore.edit { it[KEY_LAST_NAME] = lastName }
    }

    fun getFullName(firstName: String?, lastName: String?): String {
        return "${firstName.orEmpty()} ${lastName.orEmpty()}".trim()
    }

    suspend fun getEmail(): String? = dataStore.data.map { it[KEY_EMAIL] }.first()

    suspend fun editEmail(email: String) {
        dataStore.edit { it[KEY_EMAIL] = email }
    }

    suspend fun getUsername(): String? = dataStore.data.map { it[KEY_USERNAME] }.first()

    suspend fun editUsername(username: String) {
        dataStore.edit { it[KEY_USERNAME] = username }
    }

    suspend fun setOnboardingDone(onboardingDone: Boolean) {
        dataStore.edit { it[KEY_ONBOARDING_DONE] = onboardingDone }
    }

    suspend fun isOnboardingDone(): Boolean =
        dataStore.data.map { it[KEY_ONBOARDING_DONE] ?: false }.first()

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}