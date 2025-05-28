package com.jin.honey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jin.honey.feature.favorite.ui.FavoriteViewModel
import com.jin.honey.feature.firestoreimpl.data.FireStoreDataSourceImpl
import com.jin.honey.feature.food.data.FoodRepositoryImpl
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.usecase.GetAllFoodUseCase
import com.jin.honey.feature.home.ui.HomeViewModel
import com.jin.honey.feature.mypage.ui.MyPageViewModel
import com.jin.honey.feature.navigation.Screens
import com.jin.honey.feature.network.UnsplashApiClient
import com.jin.honey.feature.order.ui.OrderViewModel
import com.jin.honey.feature.unsplashimpl.data.UnsplashDataSourceImpl
import com.jin.honey.main.ui.MainScreen
import com.jin.honey.main.ui.MainViewModel
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
        enableEdgeToEdge()
        setContent {
            HoneyTheme {
                val firestore = Firebase.firestore
                val unsplashApi = UnsplashApiClient.createService()
                AppNavigator(
                    FoodRepositoryImpl(FireStoreDataSourceImpl(firestore), UnsplashDataSourceImpl(unsplashApi)),
                )
            }
        }
    }
}

@Composable
fun AppNavigator(foodRepository: FoodRepository) {
    val navController = rememberNavController()
    val mainViewModel = MainViewModel()
    val homeViewModel = HomeViewModel(GetAllFoodUseCase(foodRepository))
    val orderViewModel = OrderViewModel()
    val favoriteViewModel = FavoriteViewModel()
    val myPageViewModel = MyPageViewModel()
    NavHost(navController, Screens.Main.route) {
        composable(Screens.Main.route) {
            MainScreen(
                mainViewModel = mainViewModel,
                homeViewModel = homeViewModel,
                orderViewModel = orderViewModel,
                favoriteViewModel = favoriteViewModel,
                myPageViewModel = myPageViewModel
            ) {
                // navigate
            }
        }
    }
}
