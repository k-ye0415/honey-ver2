package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.feature.ingredient.model.IngredientPreview

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategories(): Result<List<String>>
    suspend fun findAllCategoriesAndMenus(): Result<List<Food>>
    suspend fun findIngredientByMenuName(menuName: String): Result<IngredientPreview>
    suspend fun findRecipeByMenuName(menuName: String): Result<Recipe>
}
