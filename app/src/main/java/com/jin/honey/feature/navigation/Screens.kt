package com.jin.honey.feature.navigation

sealed class Screens(val route: String) {
    object Home : Screens("homeScreen")
    object Order : Screens("orderHistoryScreen")
    object Favorite : Screens("favoriteScreen")
    object MyPage : Screens("myPageScreen")
    object Category : Screens("categoryScreen")
}
