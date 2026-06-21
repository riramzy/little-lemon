package com.riramzy.littlelemon.ui.screens.auth

sealed interface UserState {
    object Idle : UserState
    object Loading : UserState
    object Success : UserState
    data class Error(val message: String) : UserState
}