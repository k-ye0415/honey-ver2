package com.jin.honey.feature.navigation

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboardingScreen")
    object Main : Screens("main")
    object Home : Screens("homeScreen")
    object Order : Screens("orderHistoryScreen")
    object Favorite : Screens("favoriteScreen")
    object MyPage : Screens("myPageScreen")
    object Category : Screens("categoryScreen/{$CATEGORY}") {
        fun createRoute(categoryName: String): String = "categoryScreen/$categoryName"
    }

    object Ingredient : Screens("ingredientScreen/{$MENU_MANE}") {
        fun createRoute(menuName: String): String = "ingredientScreen/$menuName"
    }

    companion object {
        const val CATEGORY = "category"
        const val MENU_MANE = "menuName"
    }
}
