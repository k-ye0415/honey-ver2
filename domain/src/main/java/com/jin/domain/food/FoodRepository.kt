package com.jin.domain.food

import com.jin.domain.food.model.Food
import com.jin.domain.food.model.IngredientPreview
import com.jin.domain.food.model.MenuPreview

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategoryNames(): List<String>
    suspend fun fetchAllFoodList(): List<Food>
    suspend fun findIngredientByMenuName(menuName: String):  IngredientPreview?
    suspend fun fetchRandomMenus(): List<MenuPreview>
    suspend fun searchMenuByKeyword(keyword: String): List<MenuPreview>
    suspend fun findMenuByMenuName(menuName: String):  MenuPreview?
    suspend fun fetchMenuImage(menuName: String): String
}
