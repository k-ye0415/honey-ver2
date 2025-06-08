package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.MenuPreview
import com.jin.honey.feature.ingredient.model.IngredientPreview
import com.jin.honey.feature.recipe.model.RecipePreview

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategories(): Result<List<String>>
    suspend fun findAllCategoriesAndMenus(): Result<List<Food>>
    suspend fun findIngredientByMenuName(menuName: String): Result<IngredientPreview>
    suspend fun findRecipeByMenuName(menuName: String): Result<RecipePreview>
    suspend fun findRandomMenus(): Result<List<MenuPreview>>
    suspend fun searchMenuByKeyword(keyword: String): Result<List<MenuPreview>>
}
