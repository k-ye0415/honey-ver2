package com.jin.honey.feature.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.usecase.AddIngredientToCartUseCase
import com.jin.honey.feature.cart.domain.usecase.ChangeQuantityOfCartUseCase
import com.jin.honey.feature.cart.domain.usecase.GetCartItemsUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveCartItemUseCase
import com.jin.honey.feature.category.ui.CategoryScreen
import com.jin.honey.feature.category.ui.CategoryViewModel
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.usecase.SearchDistrictUseCase
import com.jin.honey.feature.favorite.ui.FavoriteScreen
import com.jin.honey.feature.favorite.ui.FavoriteViewModel
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.usecase.GetAllFoodsUseCase
import com.jin.honey.feature.food.domain.usecase.GetCategoryNamesUseCase
import com.jin.honey.feature.food.domain.usecase.GetIngredientUseCase
import com.jin.honey.feature.food.domain.usecase.GetRecipeUseCase
import com.jin.honey.feature.food.domain.usecase.SyncAllMenuUseCase
import com.jin.honey.feature.home.ui.HomeScreen
import com.jin.honey.feature.home.ui.HomeViewModel
import com.jin.honey.feature.ingredient.ui.IngredientScreen
import com.jin.honey.feature.ingredient.ui.IngredientViewModel
import com.jin.honey.feature.mypage.ui.MyPageScreen
import com.jin.honey.feature.mypage.ui.MyPageViewModel
import com.jin.honey.feature.onboarding.ui.OnboardingScreen
import com.jin.honey.feature.onboarding.ui.OnboardingViewModel
import com.jin.honey.feature.order.ui.OrderScreen
import com.jin.honey.feature.order.ui.OrderViewModel
import com.jin.honey.feature.recipe.ui.RecipeScreen
import com.jin.honey.feature.recipe.ui.RecipeViewModel
import com.jin.honey.feature.ui.systemBottomBarHeightDp

@Composable
fun RootNavigation(
    foodRepository: FoodRepository,
    preferencesRepository: PreferencesRepository,
    cartRepository: CartRepository,
    districtRepository: DistrictRepository
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Onboarding.route
    ) {
        composable(Screens.Onboarding.route) {
            val onboardingViewModel = OnboardingViewModel(SyncAllMenuUseCase(foodRepository), preferencesRepository)
            OnboardingScreen(onboardingViewModel) {
                navController.navigate(Screens.Main.route) {
                    popUpTo(Screens.Onboarding.route) {
                        this.inclusive = true
                    }
                }
            }
        }
        // bottomTapBar layout
        composable(Screens.Main.route) {
            BottomTabNavigator(navController, foodRepository, cartRepository, districtRepository)
        }
        composable(
            route = Screens.Ingredient.route,
            arguments = listOf(
                navArgument(Screens.MENU_MANE) { type = NavType.StringType }
            )
        ) {
            val menuName = it.arguments?.getString(Screens.MENU_MANE).orEmpty()
            val viewModel = remember {
                IngredientViewModel(
                    GetIngredientUseCase(foodRepository),
                    AddIngredientToCartUseCase(cartRepository)
                )
            }
            IngredientScreen(
                viewModel,
                menuName,
                onNavigateToCategory = { navController.popBackStack() },
                onNavigateToRecipe = { menuName ->
                    val route = Screens.Recipe.createRoute(menuName)
                    navController.navigate(route)
                })
        }
        composable(
            route = Screens.Recipe.route,
            arguments = listOf(
                navArgument(Screens.MENU_MANE) { type = NavType.StringType }
            )
        ) {
            val menuName = it.arguments?.getString(Screens.MENU_MANE).orEmpty()
            val viewModel = remember { RecipeViewModel(GetRecipeUseCase(foodRepository)) }
            RecipeScreen(viewModel, menuName) { navController.popBackStack() }
        }
    }
}

@Composable
fun BottomTabNavigator(
    navController: NavHostController,
    foodRepository: FoodRepository,
    cartRepository: CartRepository,
    districtRepository: DistrictRepository
) {
    val tabNavController = rememberNavController()
    Scaffold(
        modifier = Modifier.padding(bottom = systemBottomBarHeightDp()),
        bottomBar = { BottomTabBar(tabNavController) }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.route) {
                val viewModel = remember { HomeViewModel(GetCategoryNamesUseCase(foodRepository), SearchDistrictUseCase(districtRepository)) }
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToFoodCategory = {
                        val route = Screens.Category.createRoute(it.categoryName)
                        tabNavController.navigate(route)
                    }
                )
            }

            composable(
                route = Screens.Category.route,
                arguments = listOf(
                    navArgument(Screens.CATEGORY) { type = NavType.StringType }
                )
            ) {
                val categoryName = it.arguments?.getString(Screens.CATEGORY) ?: CategoryType.Burger.categoryName
                val viewModel = remember {
                    CategoryViewModel(
                        GetAllFoodsUseCase(foodRepository),
                        AddIngredientToCartUseCase(cartRepository)
                    )
                }
                CategoryScreen(
                    viewModel = viewModel,
                    categoryName = categoryName,
                    onNavigateToIngredient = { menuName ->
                        val route = Screens.Ingredient.createRoute(menuName)
                        navController.navigate(route)
                    },
                    onNavigateToRecipe = { menuName ->
                        val route = Screens.Recipe.createRoute(menuName)
                        navController.navigate(route)
                    }
                )
            }
            composable(Screens.Order.route) {
                val viewModel = remember {
                    OrderViewModel(
                        GetCartItemsUseCase(cartRepository),
                        RemoveCartItemUseCase(cartRepository),
                        ChangeQuantityOfCartUseCase(cartRepository)
                    )
                }
                OrderScreen(viewModel)
            }
            composable(Screens.Favorite.route) { FavoriteScreen(FavoriteViewModel()) }
            composable(Screens.MyPage.route) { MyPageScreen(MyPageViewModel()) }
        }
    }
}

@Composable
private fun BottomTabBar(navController: NavHostController) {
    val currentDestination by navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route
    val selectedIndex = TabMenu.entries.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    TabRow(selectedTabIndex = selectedIndex) {
        TabMenu.entries.forEachIndexed { index, tab ->
            Tab(
                selected = index == selectedIndex,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            ) {
                Icon(imageVector = tab.icon, contentDescription = tab.title)
                Text(tab.title)
            }
        }
    }
}

enum class TabMenu(val route: String, val title: String, val icon: ImageVector) {
    HOME("homeScreen", "home", Icons.Default.Home),
    ORDER("orderHistoryScreen", "order", Icons.Default.Payments),
    FAVORITE("favoriteScreen", "Favorite", Icons.Default.FavoriteBorder),
    MY_PAGE("myPageScreen", "myPage", Icons.Default.Person)
}
