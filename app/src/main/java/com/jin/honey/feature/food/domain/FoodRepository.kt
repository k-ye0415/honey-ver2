package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.ingredient.model.IngredientPreview
import com.jin.honey.feature.recipe.domain.model.RecipePreview

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategoryNames(): List<String>
    suspend fun fetchAllFoodList(): List<Food>
    suspend fun findIngredientByMenuName(menuName: String): IngredientPreview?
    suspend fun findRecipeByMenuName(menuName: String): RecipePreview?
    suspend fun fetchRandomMenus(): List<MenuPreview>
    suspend fun searchMenuByKeyword(keyword: String): List<MenuPreview>
    suspend fun findMenuByMenuName(menuName: String): MenuPreview?
    suspend fun fetchMenuImage(menuName: String): String
}
