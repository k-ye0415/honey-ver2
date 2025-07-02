package com.jin.ui.navigation

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboardingScreen")
    object Main : Screens("main")
    object Home : Screens("homeScreen")
    object AddressDetail : Screens("addressDetailScreen")
    object Order : Screens("orderHistoryScreen")
    object OrderDetail : Screens("orderDetailScreen")
    object Favorite : Screens("favoriteScreen")
    object MyPage : Screens("myPageScreen")
    object Category : Screens("categoryScreen/{$CATEGORY}") {
        fun createRoute(categoryName: String): String = "categoryScreen/$categoryName"
    }

    object Ingredient : Screens("ingredientScreen/{$MENU_MANE}") {
        fun createRoute(menuName: String): String = "ingredientScreen/$menuName"
    }

    object Recipe : Screens("recipeScreen/{$MENU_MANE}") {
        fun createRoute(menuName: String): String = "recipeScreen/$menuName"
    }

    object FoodSearch : Screens("foodSearchScreen")
    object Review : Screens("reviewScreen/{$MENU_MANE}") {
        fun createRoute(menuName: String): String = "reviewScreen/$menuName"
    }

    object PaymentDetail : Screens("paymentDetailScreen/{$ORDER_KEY}") {
        fun createRoute(orderKey: String): String = "paymentDetailScreen/$orderKey"
    }

    object ReviewWrite : Screens("reviewWriteScreen/{$ORDER_KEY}") {
        fun createRoute(orderKey: String): String = "reviewWriteScreen/$orderKey"
    }

    object ChatBot : Screens("chatBotScreen/{$MENU_MANE}") {
        fun createRoute(menuName: String): String = "chatBotScreen/$menuName"
    }

    object MyRecipe : Screens("myRecipeScreen/{$MENU_MANE}") {
        fun createRoute(menuName: String): String = "myRecipeScreen/$menuName"
    }

    companion object {
        const val CATEGORY = "category"
        const val MENU_MANE = "menuName"
        const val ADDRESS = "address"
        const val RECOMMEND_MENUS = "recommendMenus"
        const val ORDER_KEY = "orderKey"
    }
}
