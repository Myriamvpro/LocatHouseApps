package com.example.houselocatapp.navigation

sealed class Views (val views: String) {
    object AdminView : Views("AdminView")
    object AddMaison : Views("AddMaison")
    object UpdateMaison : Views("UpdateMaison")
    object maison_view: Views("maison_view")
    object ProfileView: Views("ProfileView")
}