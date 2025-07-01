package com.jin.domain.repositories

import com.jin.domain.model.food.Food
import com.jin.domain.model.food.IngredientPreview
import com.jin.domain.model.food.MenuPreview

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategoryNames(): List<String>
    suspend fun fetchAllFoodList(): List<Food>
    suspend fun findIngredientByMenuName(menuName: String):  com.jin.domain.model.food.IngredientPreview?
    suspend fun fetchRandomMenus(): List<MenuPreview>
    suspend fun searchMenuByKeyword(keyword: String): List< MenuPreview>
    suspend fun findMenuByMenuName(menuName: String):  MenuPreview?
    suspend fun fetchMenuImage(menuName: String): String
}
