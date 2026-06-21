package com.riramzy.littlelemon.utils

sealed interface UserStatus {
    object Unauthenticated : UserStatus
    data class Authenticated(val uid: String, val email: String) : UserStatus
}