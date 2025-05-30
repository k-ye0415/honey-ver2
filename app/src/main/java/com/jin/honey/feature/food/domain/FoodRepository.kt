package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Menu

interface FoodRepository {
    suspend fun syncAllMenu()
    suspend fun findCategories(): Result<List<String>>
    suspend fun findAllCategoryMenus(): Result<List<Category>>
    suspend fun findMenuIngredient(menuName: String): Result<Menu>
}
