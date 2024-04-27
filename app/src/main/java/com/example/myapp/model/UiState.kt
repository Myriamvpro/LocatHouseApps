package com.example.myapp.model

import com.example.myapp.data.signIn.UserData

data class UiState(
    val isSignInSuccessfull : Boolean = false,
    val signinError : String? = null,
    val currentUser: UserData? = null,
    val isLoading : Boolean = false,
    val maison : List<Maison> = emptyList()
)

