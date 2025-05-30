package com.jin.honey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
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
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jin.honey.feature.category.ui.CategoryScreen
import com.jin.honey.feature.category.ui.CategoryViewModel
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.datastore.data.PreferencesRepositoryImpl
import com.jin.honey.feature.favorite.ui.FavoriteScreen
import com.jin.honey.feature.favorite.ui.FavoriteViewModel
import com.jin.honey.feature.firestoreimpl.data.FireStoreDataSourceImpl
import com.jin.honey.feature.food.data.FoodRepositoryImpl
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.usecase.GetAllMenusUseCase
import com.jin.honey.feature.food.domain.usecase.GetCategoryUseCase
import com.jin.honey.feature.food.domain.usecase.GetMenuIngredientUseCase
import com.jin.honey.feature.food.domain.usecase.SyncAllMenuUseCase
import com.jin.honey.feature.home.ui.HomeScreen
import com.jin.honey.feature.home.ui.HomeViewModel
import com.jin.honey.feature.ingredient.ui.IngredientScreen
import com.jin.honey.feature.ingredient.ui.IngredientViewModel
import com.jin.honey.feature.mypage.ui.MyPageScreen
import com.jin.honey.feature.mypage.ui.MyPageViewModel
import com.jin.honey.feature.navigation.Screens
import com.jin.honey.feature.onboarding.ui.OnboardingScreen
import com.jin.honey.feature.onboarding.ui.OnboardingViewModel
import com.jin.honey.feature.order.ui.OrderScreen
import com.jin.honey.feature.order.ui.OrderViewModel
import com.jin.honey.ui.theme.HoneyTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        lifecycleScope.launch {
            delay(1000L)
            keepSplashScreen = false
        }
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "honey_db"
        ).build()
        enableEdgeToEdge()
        setContent {
            HoneyTheme {
                val firestore = Firebase.firestore
                RootNavigation(
                    FoodRepositoryImpl(
                        db.foodTrackingDataSource(),
                        FireStoreDataSourceImpl(firestore)
                    ),
                    PreferencesRepositoryImpl(this)
                )
            }
        }
    }
}

@Composable
fun RootNavigation(foodRepository: FoodRepository, preferencesRepository: PreferencesRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Onboarding.route
    ) {
        composable(Screens.Onboarding.route) {
            val onboardingViewModel = OnboardingViewModel(SyncAllMenuUseCase(foodRepository), preferencesRepository)
            OnboardingScreen(onboardingViewModel) {
                navController.navigate(Screens.Main.route)
            }
        }
        composable(Screens.Main.route) {
            AppNavigator(navController, foodRepository)
        }
        composable(Screens.Ingredient.route) {
            val menuName = it.arguments?.getString("menuName").orEmpty()
            val ingredientViewModel = remember { IngredientViewModel(GetMenuIngredientUseCase(foodRepository)) }
            IngredientScreen(ingredientViewModel, menuName)
        }
    }
}

@Composable
fun AppNavigator(navController: NavHostController, foodRepository: FoodRepository) {
    val tabNavController = rememberNavController()
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
    Scaffold(
        modifier = Modifier.padding(bottom = navigationBarHeightDp),
        bottomBar = {
            BottomTabBar(tabNavController)
        }
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screens.Home.route) {
                val viewModel = remember { HomeViewModel(GetCategoryUseCase(foodRepository)) }
                HomeScreen(viewModel) {
                    val route = Screens.Category.createRoute(it.categoryName)
                    tabNavController.navigate(route)
                }
            }

            composable(
                route = Screens.Category.route,
                arguments = listOf(
                    navArgument("category") { type = NavType.StringType }
                )
            ) {
                val categoryName = it.arguments?.getString("category") ?: CategoryType.Burger.categoryName
                val viewModel = remember { CategoryViewModel(GetAllMenusUseCase(foodRepository)) }
                CategoryScreen(viewModel, categoryName) {
                    val route = Screens.Ingredient.createRoute(it)
                    navController.navigate(route)
                }
            }
            composable(Screens.Order.route) { OrderScreen(OrderViewModel()) }
            composable(Screens.Favorite.route) { FavoriteScreen(FavoriteViewModel()) }
            composable(Screens.MyPage.route) { MyPageScreen(MyPageViewModel()) }
        }
    }
}

@Composable
fun BottomTabBar(navController: NavHostController) {
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
