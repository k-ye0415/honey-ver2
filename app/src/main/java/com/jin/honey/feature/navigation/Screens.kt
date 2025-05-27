package com.jin.honey.feature.navigation

sealed class Screens(val route: String) {
    object Main : Screens("mainScreen")
    object Order : Screens("orderHistoryScreen")
    object Favorite : Screens("favoriteScreen")
    object MyHome : Screens("myHomeScreen")
}
