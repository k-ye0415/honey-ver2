package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.ingredient.model.IngredientPreview

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategoryNames(): List<String>
    suspend fun fetchAllFoodList(): List<Food>
    suspend fun findIngredientByMenuName(menuName: String): IngredientPreview?
    suspend fun fetchRandomMenus(): List<MenuPreview>
    suspend fun searchMenuByKeyword(keyword: String): List<MenuPreview>
    suspend fun findMenuByMenuName(menuName: String): MenuPreview?
    suspend fun fetchMenuImage(menuName: String): String
}
