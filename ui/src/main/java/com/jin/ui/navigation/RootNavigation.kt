package com.jin.ui.navigation

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
import com.jin.domain.address.AddressRepository
import com.jin.domain.address.model.SearchAddress
import com.jin.domain.cart.CartRepository
import com.jin.domain.chat.ChatRepository
import com.jin.domain.favorite.FavoriteRepository
import com.jin.domain.food.FoodRepository
import com.jin.domain.food.model.CategoryType
import com.jin.domain.food.model.MenuPreview
import com.jin.domain.launch.LaunchRepository
import com.jin.domain.order.OrderRepository
import com.jin.domain.recipe.RecipeRepository
import com.jin.domain.review.ReviewRepository
import com.jin.domain.search.SearchRepository
import com.jin.domain.usecase.AddIngredientToCartUseCase
import com.jin.domain.usecase.ChangeCurrentAddressUseCase
import com.jin.domain.usecase.ChangeQuantityOfCartUseCase
import com.jin.domain.usecase.EnsureInitialMessageUseCase
import com.jin.domain.usecase.GetAddressesUseCase
import com.jin.domain.usecase.GetAllFoodsUseCase
import com.jin.domain.usecase.GetCartItemsUseCase
import com.jin.domain.usecase.GetCategoryNamesUseCase
import com.jin.domain.usecase.GetFavoriteMenuUseCase
import com.jin.domain.usecase.GetIngredientUseCase
import com.jin.domain.usecase.GetMessageListUseCase
import com.jin.domain.usecase.GetOrderDetailUseCase
import com.jin.domain.usecase.GetOrderHistoriesUseCase
import com.jin.domain.usecase.GetRankingReviewUseCase
import com.jin.domain.usecase.GetRecentlyMenuUseCase
import com.jin.domain.usecase.GetRecipeUseCase
import com.jin.domain.usecase.GetRecommendMenuUseCase
import com.jin.domain.usecase.GetRecommendRecipeUseCase
import com.jin.domain.usecase.GetReviewUseCase
import com.jin.domain.usecase.GetReviewWithIngredientUseCase
import com.jin.domain.usecase.PayAndOrderUseCase
import com.jin.domain.usecase.RemoveIngredientInCartItemUseCase
import com.jin.domain.usecase.RemoveMenuInCartUseCase
import com.jin.domain.usecase.SaveAddressUseCase
import com.jin.domain.usecase.SaveMyRecipeUseCase
import com.jin.domain.usecase.SearchAddressUseCase
import com.jin.domain.usecase.SearchMenusUseCase
import com.jin.domain.usecase.SendMessageUseCase
import com.jin.domain.usecase.SyncAllMenuUseCase
import com.jin.domain.usecase.SyncRecipesUseCase
import com.jin.domain.usecase.SyncReviewsUseCase
import com.jin.domain.usecase.WriteReviewUseCase
import com.jin.systemBottomBarHeightDp
import com.jin.ui.R
import com.jin.ui.address.AddressDetailScreen
import com.jin.ui.address.AddressViewModel
import com.jin.ui.category.CategoryScreen
import com.jin.ui.category.CategoryViewModel
import com.jin.ui.chat.ChatScreen
import com.jin.ui.chat.ChatViewModel
import com.jin.ui.favorite.FavoriteScreen
import com.jin.ui.favorite.FavoriteViewModel
import com.jin.ui.foodsearch.FoodSearchScreen
import com.jin.ui.foodsearch.FoodSearchViewModel
import com.jin.ui.home.HomeScreen
import com.jin.ui.home.HomeViewModel
import com.jin.ui.ingredient.IngredientScreen
import com.jin.ui.ingredient.IngredientViewModel
import com.jin.ui.mypage.MyPageScreen
import com.jin.ui.mypage.MyPageViewModel
import com.jin.ui.onboarding.OnboardingScreen
import com.jin.ui.onboarding.OnboardingViewModel
import com.jin.ui.order.OrderScreen
import com.jin.ui.order.OrderViewModel
import com.jin.ui.order.detail.OrderDetailScreen
import com.jin.ui.order.detail.OrderDetailViewModel
import com.jin.ui.payment.PaymentDetailScreen
import com.jin.ui.payment.PaymentDetailViewModel
import com.jin.ui.recipe.RecipeScreen
import com.jin.ui.recipe.RecipeViewModel
import com.jin.ui.recipe.myrecipe.MyRecipeScreen
import com.jin.ui.recipe.myrecipe.MyRecipeViewModel
import com.jin.ui.review.ReviewScreen
import com.jin.ui.review.ReviewViewModel
import com.jin.ui.review.write.ReviewWriteScreen
import com.jin.ui.review.write.ReviewWriteViewModel
import com.jin.ui.theme.PointColor
import com.jin.ui.theme.UnSelectedTabColor

@Composable
fun RootNavigation(
    foodRepository: FoodRepository,
    launchRepository: LaunchRepository,
    searchRepository: SearchRepository,
    favoriteRepository: FavoriteRepository,
    cartRepository: CartRepository,
    addressRepository: AddressRepository,
    orderRepository: OrderRepository,
    reviewRepository: ReviewRepository,
    recipeRepository: RecipeRepository,
    chatRepository: ChatRepository,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Onboarding.route
    ) {
        composable(Screens.Onboarding.route) {
            val onboardingViewModel = OnboardingViewModel(
                launchRepository,
                SyncAllMenuUseCase(foodRepository),
                SyncReviewsUseCase(reviewRepository),
                SyncRecipesUseCase(recipeRepository)
            )
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
                addressRepository,
                orderRepository,
                favoriteRepository,
                recipeRepository,
                reviewRepository
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
                    favoriteRepository,
                    GetReviewUseCase(reviewRepository)
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
                },
                onNavigateToMyRecipe = {isEditMode, menuName ->
                    val route = Screens.MyRecipe.createRoute(isEditMode, menuName)
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
            val viewModel = remember { RecipeViewModel(GetRecipeUseCase(recipeRepository, foodRepository)) }
            RecipeScreen(
                viewModel = viewModel,
                menuName = menuName,
                onNavigateToBack = { navController.popBackStack() },
                onNavigateToChatBot = {
                    val route = Screens.ChatBot.createRoute(menuName)
                    navController.navigate(route)
                },
                onNavigateToMyRecipe = { isEditMode, menuName ->
                    val route = Screens.MyRecipe.createRoute(isEditMode, menuName)
                    navController.navigate(route)
                }
            )
        }
        composable(Screens.AddressDetail.route) {
            val searchAddress =
                navController.previousBackStackEntry?.savedStateHandle?.get<SearchAddress>(Screens.ADDRESS)
            val viewModel = remember {
                AddressViewModel(
                    SaveAddressUseCase(addressRepository)
                )
            }
            AddressDetailScreen(
                searchAddress,
                viewModel,
                onNavigateToMain = { navController.popBackStack() })
        }
        composable(Screens.FoodSearch.route) {
            val menus =
                navController.previousBackStackEntry?.savedStateHandle?.get<List<MenuPreview>>(Screens.RECOMMEND_MENUS)
            val viewModel = remember { FoodSearchViewModel(searchRepository, SearchMenusUseCase(foodRepository)) }
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
                    GetAddressesUseCase(addressRepository),
                    SearchAddressUseCase(addressRepository),
                    GetCartItemsUseCase(cartRepository),
                    RemoveIngredientInCartItemUseCase(cartRepository),
                    ChangeQuantityOfCartUseCase(cartRepository),
                    RemoveMenuInCartUseCase(cartRepository),
                    PayAndOrderUseCase(orderRepository, cartRepository),
                    ChangeCurrentAddressUseCase(addressRepository)
                )
            }
            OrderDetailScreen(
                viewModel = viewModel,
                onNavigateToLocationDetail = { address ->
                    navController.currentBackStackEntry?.savedStateHandle?.set(Screens.ADDRESS, address)
                    navController.navigate(Screens.AddressDetail.route)
                },
                onNavigateToOrder = { navController.popBackStack() }
            )
        }
        composable(
            route = Screens.ReviewWrite.route,
            arguments = listOf(
                navArgument(Screens.ORDER_KEY) { type = NavType.StringType }
            )
        ) {
            val orderKey = it.arguments?.getString(Screens.ORDER_KEY).orEmpty()
            val viewModel = remember {
                ReviewWriteViewModel(
                    GetOrderDetailUseCase(orderRepository),
                    WriteReviewUseCase(reviewRepository)
                )
            }
            ReviewWriteScreen(
                viewModel = viewModel,
                orderKey = orderKey,
                onNavigateToOrder = { navController.popBackStack() })
        }
        composable(
            route = Screens.Review.route,
            arguments = listOf(
                navArgument(Screens.MENU_MANE) { type = NavType.StringType }
            )
        ) {
            val menuName = it.arguments?.getString(Screens.MENU_MANE).orEmpty()
            val viewModel = remember {
                ReviewViewModel(
                    GetReviewWithIngredientUseCase(reviewRepository, orderRepository)
                )
            }
            ReviewScreen(viewModel, menuName)
        }
        composable(
            route = Screens.PaymentDetail.route,
            arguments = listOf(
                navArgument(Screens.ORDER_KEY) { type = NavType.StringType }
            )
        ) {
            val orderKey = it.arguments?.getString(Screens.ORDER_KEY).orEmpty()
            val viewModel = remember { PaymentDetailViewModel(GetOrderDetailUseCase(orderRepository)) }
            PaymentDetailScreen(
                viewModel = viewModel,
                orderKey = orderKey,
                onNavigateToIngredient = { menuName ->
                    val route = Screens.Ingredient.createRoute(menuName)
                    navController.navigate(route)
                }
            )
        }
        composable(
            route = Screens.ChatBot.route,
            arguments = listOf(
                navArgument(Screens.MENU_MANE) { type = NavType.StringType }
            )
        ) {
            val menuName = it.arguments?.getString(Screens.MENU_MANE).orEmpty()
            val viewModel = remember {
                ChatViewModel(
                    GetMessageListUseCase(chatRepository),
                    EnsureInitialMessageUseCase(chatRepository),
                    SendMessageUseCase(chatRepository),
                )
            }
            ChatScreen(viewModel, menuName)
        }
        composable(
            route = Screens.MyRecipe.route,
            arguments = listOf(
                navArgument(Screens.MENU_MANE) { type = NavType.StringType }
            )
        ) {
            val menuName = it.arguments?.getString(Screens.MENU_MANE).orEmpty()
            val viewModel = remember { MyRecipeViewModel(SaveMyRecipeUseCase(recipeRepository)) }
            MyRecipeScreen(
                viewModel = viewModel,
                menuName = menuName,
                onNavigateToBackStack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
fun BottomTabNavigator(
    navController: NavHostController,
    foodRepository: FoodRepository,
    cartRepository: CartRepository,
    addressRepository: AddressRepository,
    orderRepository: OrderRepository,
    favoriteRepository: FavoriteRepository,
    recipeRepository: RecipeRepository,
    reviewRepository: ReviewRepository,
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
                        SearchAddressUseCase(addressRepository),
                        GetAddressesUseCase(addressRepository),
                        GetRecommendMenuUseCase(foodRepository),
                        GetRecommendRecipeUseCase(recipeRepository, foodRepository),
                        GetRankingReviewUseCase(reviewRepository, foodRepository),
                        ChangeCurrentAddressUseCase(addressRepository)
                    )
                }
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToFoodCategory = {
                        val route = Screens.Category.createRoute(it.categoryName)
                        tabNavController.navigate(route)
                    },
                    onNavigateToAddress = { address ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(Screens.ADDRESS, address)
                        navController.navigate(Screens.AddressDetail.route)
                    },
                    onNavigateToFoodSearch = { menus ->
                        navController.currentBackStackEntry?.savedStateHandle?.set(Screens.RECOMMEND_MENUS, menus)
                        navController.navigate(Screens.FoodSearch.route)
                    },
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

            composable(
                route = Screens.Category.route,
                arguments = listOf(
                    navArgument(Screens.CATEGORY) { type = NavType.StringType }
                )
            ) {
                val categoryName = it.arguments?.getString(Screens.CATEGORY) ?: CategoryType.Burger.categoryName
                val viewModel = remember {
                    CategoryViewModel(
                        GetAddressesUseCase(addressRepository),
                        SearchAddressUseCase(addressRepository),
                        GetAllFoodsUseCase(foodRepository),
                        AddIngredientToCartUseCase(cartRepository),
                        favoriteRepository,
                        ChangeCurrentAddressUseCase(addressRepository)
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
                        navController.navigate(Screens.AddressDetail.route)

                    }
                )
            }
            composable(Screens.Order.route) {
                val viewModel = remember {
                    OrderViewModel(
                        GetCartItemsUseCase(cartRepository),
                        RemoveIngredientInCartItemUseCase(cartRepository),
                        ChangeQuantityOfCartUseCase(cartRepository),
                        GetOrderHistoriesUseCase(orderRepository)
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
                            getFavoriteMenuUseCase = GetFavoriteMenuUseCase(
                                favoriteRepository,
                                foodRepository,
                                reviewRepository
                            ),
                            getRecentlyMenuUseCase = GetRecentlyMenuUseCase(
                                favoriteRepository,
                                foodRepository,
                                reviewRepository
                            ),
                            favoriteRepository = favoriteRepository
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
