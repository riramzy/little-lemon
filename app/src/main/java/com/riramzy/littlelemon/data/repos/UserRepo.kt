package com.riramzy.littlelemon.data.repos

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.riramzy.littlelemon.data.preferences.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class UserRepo(
    private val prefs: UserPreferences,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val isLoggedInFlow: Flow<Boolean> = prefs.isLoggedInFlow
    val isOnboardingDoneFlow: Flow<Boolean> = prefs.isOnboardingDoneFlow
    val usernameFlow: Flow<String?> = prefs.usernameFlow
    val firstNameFlow: Flow<String?> = prefs.firstNameFlow
    val lastNameFlow: Flow<String?> = prefs.lastNameFlow
    val emailFlow: Flow<String?> = prefs.emailFlow
    val profilePictureFlow: Flow<String?> = prefs.profilePictureFlow
    fun isLoggedIn(): Boolean = firebaseAuth.currentUser != null
    fun getUid(): String? = firebaseAuth.currentUser?.uid

    suspend fun register(
        username: String,
        firstName: String,
        lastName: String,
        email: String,
        password: String,
    ): Result<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password)
                .await()
            val uid = result.user?.uid ?: throw Exception("User ID is null")

            val profile = hashMapOf(
                "uid" to uid,
                "firstName" to firstName,
                "lastName" to lastName,
                "email" to email,
                "username" to username,
                "createdAt" to Timestamp.now()
            )

            runCatching {
                firestore.collection("users").document(uid).set(profile)
                    .await()
            }

            prefs.register(username, firstName, lastName, email)
            prefs.setLoggedIn(true)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password)
                .await()
            val uid = authResult.user?.uid ?: throw Exception("User ID is null")

            val documentResult = runCatching {
                firestore.collection("users").document(uid).get().await()
            }
            val document = documentResult.getOrNull()

            if (document != null && document.exists()) {
                val username = document.getString("username") ?: ""
                val firstName = document.getString("firstName") ?: ""
                val lastName = document.getString("lastName") ?: ""
                val userEmail = document.getString("email") ?: email

                prefs.register(username, firstName, lastName, userEmail)
                prefs.setLoggedIn(true)
                Result.success(Unit)
            } else {
                if (documentResult.isFailure) {
                    val localUsername = email.substringBefore("@")
                    prefs.register(
                        localUsername,
                        localUsername.replaceFirstChar { it.uppercase() },
                        "",
                        email
                    )
                    prefs.setLoggedIn(true)
                    Result.success(Unit)
                } else {
                    firebaseAuth.signOut()
                    Result.failure(Exception("User profile not found in database."))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout() {
        firebaseAuth.signOut()
        prefs.clearAll()
        prefs.setLoggedIn(false)
    }

    suspend fun deleteAccount(): Result<Unit> = runCatching {
        val user = firebaseAuth.currentUser ?: throw Exception("No user authenticated")
        val uid = user.uid

        firestore.collection("users").document(uid).delete().await()

        user.delete().await()

        prefs.clearAll()
        prefs.setLoggedIn(false)
    }

    suspend fun isOnboardingDone(): Boolean {
        return prefs.isOnboardingDone()
    }

    suspend fun setOnboardingDone(onboardingDone: Boolean) {
        prefs.setOnboardingDone(onboardingDone)
    }

    suspend fun getFirstName(): String? = prefs.getFirstName()

    suspend fun editFirstName(firstName: String) {
        prefs.editFirstName(firstName)
    }

    suspend fun getLastName(): String? = prefs.getLastName()

    suspend fun editLastName(lastName: String) {
        prefs.editLastName(lastName)
    }

    suspend fun getEmail(): String? = prefs.getEmail()

    suspend fun editEmail(email: String) {
        prefs.editEmail(email)
    }

    suspend fun getUsername(): String? = prefs.getUsername()

    suspend fun editUsername(username: String) {
        prefs.editUsername(username)
    }

    suspend fun getFullName(): String {
        return prefs.getFullName(getFirstName(), getLastName())
    }

    suspend fun saveProfilePicture(uri: String) {
        prefs.saveProfilePicture(uri)
    }

    suspend fun getProfilePicture(): String? {
        return prefs.getProfilePicture()
    }
}