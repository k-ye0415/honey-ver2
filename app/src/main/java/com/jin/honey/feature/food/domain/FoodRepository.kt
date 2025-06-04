package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.Recipe

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategories(): Result<List<String>>
    suspend fun findAllCategoriesAndMenus(): Result<List<Food>>
    suspend fun findIngredientByMenuName(menuName: String): Result<Menu>
    suspend fun findRecipeByMenuName(menuName: String): Result<Recipe>
}
