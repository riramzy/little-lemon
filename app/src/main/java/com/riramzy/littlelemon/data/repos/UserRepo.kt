package com.riramzy.littlelemon.data.repos

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.riramzy.littlelemon.data.preferences.UserPreferences
import com.riramzy.littlelemon.utils.UserStatus
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepo(
    private val prefs: UserPreferences,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val authState: Flow<UserStatus> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser

            if (user == null) {
                trySend(UserStatus.Unauthenticated)
            } else {
                trySend(UserStatus.Authenticated(user.uid, user.email ?: ""))
            }
        }

        firebaseAuth.addAuthStateListener(listener)
        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

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

            firestore.collection("users").document(uid).set(profile)
                .await()

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
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .await()

            prefs.setLoggedIn(true)

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        firebaseAuth.signOut()
        prefs.clearAll()
        prefs.setLoggedIn(false)
    }

    fun deleteAccount() {
        prefs.clearAll()
        prefs.setLoggedIn(false)
    }

    fun isOnboardingDone(): Boolean {
        return prefs.isOnboardingDone()
    }

    fun setOnboardingDone(onboardingDone: Boolean) {
        prefs.setOnboardingDone(onboardingDone)
    }

    fun getFirstName(): String? = prefs.getFirstName()

    fun editFirstName(firstName: String) {
        prefs.editFirstName(firstName)
    }

    fun getLastName(): String? = prefs.getLastName()

    fun editLastName(lastName: String) {
        prefs.editLastName(lastName)
    }

    fun getEmail(): String? = prefs.getEmail()

    fun editEmail(email: String) {
        prefs.editEmail(email)
    }

    fun getUsername(): String? = prefs.getUsername()

    fun editUsername(username: String) {
        prefs.editUsername(username)
    }

    fun getFullName(): String = prefs.getFullName()

    fun saveProfilePicture(uri: String) {
        prefs.saveProfilePicture(uri)
    }

    fun getProfilePicture(): String? {
        return prefs.getProfilePicture()
    }
}