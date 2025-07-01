package com.jin.honey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jin.database.AppDatabase
import com.jin.data.address.AddressRepositoryImpl
import com.jin.data.addressimpl.AddressDataSourceImpl
import com.jin.data.cart.CartRepositoryImpl
import com.jin.datastore.PreferencesRepositoryImpl
import com.jin.data.firestoreimpl.FireStoreDataSourceImpl
import com.jin.data.food.FoodRepositoryImpl
import com.jin.honey.feature.navigation.RootNavigation
import com.jin.network.kakao.KakaoMapApiClient
import com.jin.network.openai.OpenAiApiClient
import com.jin.data.chat.ChatRepositoryImpl
import com.jin.data.openaiimpl.OpenAiDataSourceImpl
import com.jin.data.order.OrderRepositoryImpl
import com.jin.data.recipe.RecipeRepositoryImpl
import com.jin.data.review.ReviewRepositoryImpl
import com.jin.ui.theme.HoneyTheme
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
                val kakaoMapApi = KakaoMapApiClient.createService(BuildConfig.KAKAO_MAP_AK)
                val openAiApi = OpenAiApiClient.createService(BuildConfig.OPEN_AI_KEY)
                RootNavigation(
                    foodRepository = FoodRepositoryImpl(
                        db.foodTrackingDataSource(),
                        FireStoreDataSourceImpl(firestore)
                    ),
                    preferencesRepository = PreferencesRepositoryImpl(this),
                    cartRepository = CartRepositoryImpl(db.cartTrackingDataSource()),
                    addressRepository = AddressRepositoryImpl(
                        AddressDataSourceImpl(kakaoMapApi),
                        db.addressTrackingDataSource()
                    ),
                    orderRepository = OrderRepositoryImpl(db.orderTrackingDataSource()),
                    reviewRepository = ReviewRepositoryImpl(
                        db = db.reviewTrackingDataSource(),
                        fireStoreDataSource = FireStoreDataSourceImpl(firestore)
                    ),
                    recipeRepository = RecipeRepositoryImpl(
                        db.recipeTrackingDataSource(),
                        FireStoreDataSourceImpl(firestore)
                    ),
                    chatRepository = ChatRepositoryImpl(
                        openAiDataSource = OpenAiDataSourceImpl(openAiApi),
                        chatTrackingDataSource = db.chatTrackingDataSource()
                    )
                )
            }
        }
    }
}
