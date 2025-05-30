package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.Menu

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategories(): Result<List<String>>
    suspend fun findAllCategoriesAndMenus(): Result<List<Food>>
    suspend fun findIngredientAt(menuName: String): Result<Menu>
}
