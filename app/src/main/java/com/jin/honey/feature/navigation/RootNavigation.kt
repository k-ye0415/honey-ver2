package com.jin.honey.feature.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jin.honey.R
import com.jin.honey.feature.address.ui.DistrictDetailScreen
import com.jin.honey.feature.address.ui.DistrictViewModel
import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.usecase.AddIngredientToCartUseCase
import com.jin.honey.feature.cart.domain.usecase.ChangeQuantityOfCartUseCase
import com.jin.honey.feature.cart.domain.usecase.GetCartItemsUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveIngredientInCartItemUseCase
import com.jin.honey.feature.cart.domain.usecase.RemoveMenuInCartUseCase
import com.jin.honey.feature.category.ui.CategoryScreen
import com.jin.honey.feature.category.ui.CategoryViewModel
import com.jin.honey.feature.datastore.PreferencesRepository
import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.usecase.GetAddressesUseCase
import com.jin.honey.feature.district.domain.usecase.GetLatestAddressUseCase
import com.jin.honey.feature.district.domain.usecase.SaveDistrictUseCase
import com.jin.honey.feature.district.domain.usecase.SearchAddressUseCase
import com.jin.honey.feature.favorite.domain.GetFavoriteMenuUseCase
import com.jin.honey.feature.favorite.domain.GetRecentlyMenuUseCase
import com.jin.honey.feature.favorite.ui.FavoriteScreen
import com.jin.honey.feature.favorite.ui.FavoriteViewModel
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.food.domain.usecase.GetAllFoodsUseCase
import com.jin.honey.feature.food.domain.usecase.GetCategoryNamesUseCase
import com.jin.honey.feature.food.domain.usecase.GetIngredientUseCase
import com.jin.honey.feature.food.domain.usecase.GetRecipeUseCase
import com.jin.honey.feature.food.domain.usecase.GetRecommendMenuUseCase
import com.jin.honey.feature.food.domain.usecase.SearchMenusUseCase
import com.jin.honey.feature.food.domain.usecase.SyncAllMenuUseCase
import com.jin.honey.feature.foodsearch.ui.FoodSearchScreen
import com.jin.honey.feature.foodsearch.ui.FoodSearchViewModel
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
import com.jin.honey.feature.orderdetail.ui.OrderDetailScreen
import com.jin.honey.feature.orderdetail.ui.OrderDetailViewModel
import com.jin.honey.feature.payment.domain.PaymentRepository
import com.jin.honey.feature.payment.domain.usecase.GetOrderDetailUseCase
import com.jin.honey.feature.payment.domain.usecase.GetOrderHistoriesUseCase
import com.jin.honey.feature.payment.domain.usecase.PayAndOrderUseCase
import com.jin.honey.feature.paymentdetail.ui.PaymentDetailScreen
import com.jin.honey.feature.paymentdetail.ui.PaymentDetailViewModel
import com.jin.honey.feature.recipe.ui.RecipeScreen
import com.jin.honey.feature.recipe.ui.RecipeViewModel
import com.jin.honey.feature.reviewwrite.ui.ReviewWriteScreen
import com.jin.honey.feature.review.ui.ReviewScreen
import com.jin.honey.feature.ui.systemBottomBarHeightDp
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.UnSelectedTabColor

@Composable
fun RootNavigation(
    foodRepository: FoodRepository,
    preferencesRepository: PreferencesRepository,
    cartRepository: CartRepository,
    districtRepository: DistrictRepository,
    paymentRepository: PaymentRepository
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
            BottomTabNavigator(
                navController,
                foodRepository,
                cartRepository,
                districtRepository,
                paymentRepository,
                preferencesRepository
            )
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
                    AddIngredientToCartUseCase(cartRepository),
                    preferencesRepository
                )
            }
            IngredientScreen(
                viewModel,
                menuName,
                onNavigateToCategory = { navController.popBackStack() },
                onNavigateToRecipe = { menuName ->
                    val route = Screens.Recipe.createRoute(menuName)
                    navController.navigate(route)
                },
                onNavigateToReview = { menuName ->
                    val route = Screens.Review.createRoute(menuName)
                    navController.navigate(route)
                }
            )
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
        composable(Screens.DistrictDetail.route) {
            val address = navController.previousBackStackEntry?.savedStateHandle?.get<Address>(Screens.ADDRESS)
            val viewModel = remember {
                DistrictViewModel(
                    SaveDistrictUseCase(districtRepository)
                )
            }
            DistrictDetailScreen(address, viewModel, onNavigateToMain = { navController.popBackStack() })
        }
        composable(Screens.FoodSearch.route) {
            val menus =
                navController.previousBackStackEntry?.savedStateHandle?.get<List<MenuPreview>>(Screens.RECOMMEND_MENUS)
            val viewModel = remember { FoodSearchViewModel(preferencesRepository, SearchMenusUseCase(foodRepository)) }
            FoodSearchScreen(
                viewModel = viewModel,
                menus = menus,
                onNavigateToIngredient = { menuName ->
                    val route = Screens.Ingredient.createRoute(menuName)
                    navController.navigate(route)
                }
            )
        }
        composable(Screens.OrderDetail.route) {
            val viewModel = remember {
                OrderDetailViewModel(
                    GetLatestAddressUseCase(districtRepository),
                    SearchAddressUseCase(districtRepository),
                    GetCartItemsUseCase(cartRepository),
                    RemoveIngredientInCartItemUseCase(cartRepository),
                    ChangeQuantityOfCartUseCase(cartRepository),
                    RemoveMenuInCartUseCase(cartRepository),
                    PayAndOrderUseCase(paymentRepository, cartRepository)
                )
            }
            OrderDetailScreen(
                viewModel = viewModel,
                onNavigateToLocationDetail = { address ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(Screens.ADDRESS, address)
                    navController.navigate(Screens.DistrictDetail.route)
                },
                onNavigateToOrder = { navController.popBackStack() }
            )
        }
        composable(
            route = Screens.ReviewWrite.route,
            arguments = listOf(
                navArgument(Screens.PAYMENT_ID) { type = NavType.IntType }
            )
        ) {
            val paymentId = it.arguments?.getInt(Screens.PAYMENT_ID) ?: 0
            ReviewWriteScreen(paymentId)
        }
        composable(
            route = Screens.Review.route,
            arguments = listOf(
                navArgument(Screens.MENU_MANE) { type = NavType.StringType }
            )
        ) {
            val menuName = it.arguments?.getString(Screens.MENU_MANE).orEmpty()
            ReviewScreen(menuName)
        }
        composable(
            route = Screens.PaymentDetail.route,
            arguments = listOf(
                navArgument(Screens.ORDER_KEY) { type = NavType.StringType }
            )
        ) {
            val orderKey = it.arguments?.getString(Screens.ORDER_KEY).orEmpty()
            val viewModel = remember { PaymentDetailViewModel(GetOrderDetailUseCase(paymentRepository)) }
            PaymentDetailScreen(
                viewModel = viewModel,
                orderKey = orderKey,
                onNavigateToIngredient = { menuName ->
                    val route = Screens.Ingredient.createRoute(menuName)
                    navController.navigate(route)
                }
            )
        }
    }
}

@Composable
fun BottomTabNavigator(
    navController: NavHostController,
    foodRepository: FoodRepository,
    cartRepository: CartRepository,
    districtRepository: DistrictRepository,
    paymentRepository: PaymentRepository,
    preferencesRepository: PreferencesRepository
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
                val viewModel = remember {
                    HomeViewModel(
                        GetCategoryNamesUseCase(foodRepository),
                        SearchAddressUseCase(districtRepository),
                        GetAddressesUseCase(districtRepository),
                        GetRecommendMenuUseCase(foodRepository)
                    )
                }
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToFoodCategory = {
                        val route = Screens.Category.createRoute(it.categoryName)
                        tabNavController.navigate(route)
                    },
                    onNavigateToAddress = { district ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(Screens.ADDRESS, district)
                        navController.navigate(Screens.DistrictDetail.route)
                    },
                    onNavigateToFoodSearch = { menus ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(Screens.RECOMMEND_MENUS, menus)
                        navController.navigate(Screens.FoodSearch.route)
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
                        GetAddressesUseCase(districtRepository),
                        SearchAddressUseCase(districtRepository),
                        GetAllFoodsUseCase(foodRepository),
                        AddIngredientToCartUseCase(cartRepository),
                        preferencesRepository
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
                    },
                    onNavigateToHome = { tabNavController.popBackStack() },
                    onNavigateToAddressDetail = { address ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(Screens.ADDRESS, address)
                        navController.navigate(Screens.DistrictDetail.route)

                    }
                )
            }
            composable(Screens.Order.route) {
                val viewModel = remember {
                    OrderViewModel(
                        GetCartItemsUseCase(cartRepository),
                        RemoveIngredientInCartItemUseCase(cartRepository),
                        ChangeQuantityOfCartUseCase(cartRepository),
                        GetOrderHistoriesUseCase(paymentRepository)
                    )
                }
                OrderScreen(
                    viewModel = viewModel,
                    onNavigateToOrder = { navController.navigate(Screens.OrderDetail.route) },
                    onNavigateToCategory = { tabNavController.navigate(Screens.Category.route) },
                    onNavigateToWriteReview = { id ->
                        val route = Screens.ReviewWrite.createRoute(id)
                        navController.navigate(route)
                    },
                    onNavigateToPaymentDetail = { orderKey ->
                        val route = Screens.PaymentDetail.createRoute(orderKey)
                        navController.navigate(route)
                    }
                )
            }
            composable(Screens.Favorite.route) {
                val viewModel =
                    remember {
                        FavoriteViewModel(
                            getFavoriteMenuUseCase = GetFavoriteMenuUseCase(preferencesRepository, foodRepository),
                            getRecentlyMenuUseCase = GetRecentlyMenuUseCase(preferencesRepository, foodRepository),
                            preferencesRepository = preferencesRepository
                        )
                    }
                FavoriteScreen(
                    viewModel = viewModel,
                    onNavigateToIngredient = { menuName ->
                        val route = Screens.Ingredient.createRoute(menuName)
                        navController.navigate(route)
                    }
                )
            }
            composable(Screens.MyPage.route) { MyPageScreen(MyPageViewModel()) }
        }
    }
}

@Composable
private fun BottomTabBar(navController: NavHostController) {
    val currentDestination by navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route
    val selectedIndex = TabMenu.entries.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    Column {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = Color.LightGray
        )
        TabRow(
            selectedTabIndex = selectedIndex,
            containerColor = Color.White,
            indicator = { },
        ) {
            TabMenu.entries.forEachIndexed { index, tab ->
                Tab(
                    selected = index == selectedIndex,
                    selectedContentColor = PointColor,
                    unselectedContentColor = UnSelectedTabColor,
                    onClick = {
                        navController.navigate(tab.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 4.dp),
                    ) {
                        Icon(
                            painter = painterResource(tab.iconRes),
                            contentDescription = tab.title,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(tab.title, fontSize = 12.sp)
                    }

                }
            }
        }
    }
}

enum class TabMenu(val route: String, val title: String, val iconRes: Int) {
    HOME("homeScreen", "홈", R.drawable.ic_tab_home),
    ORDER("orderHistoryScreen", "주문내역", R.drawable.ic_tab_bill),
    FAVORITE("favoriteScreen", "찜", R.drawable.ic_tab_favorite),
    MY_PAGE("myPageScreen", "마이페이지", R.drawable.ic_tab_my);
}
