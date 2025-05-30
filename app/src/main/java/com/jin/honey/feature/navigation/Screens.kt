package com.jin.honey.feature.navigation

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboardingScreen")
    object Main : Screens("main")
    object Home : Screens("homeScreen")
    object Order : Screens("orderHistoryScreen")
    object Favorite : Screens("favoriteScreen")
    object MyPage : Screens("myPageScreen")
    object Category : Screens("categoryScreen/{category}") {
        fun createRoute(categoryName: String): String = "categoryScreen/$categoryName"
    }

    object Ingredient : Screens("ingredientScreen/{menuName}") {
        fun createRoute(menuName: String): String = "ingredientScreen/$menuName"
    }
}
