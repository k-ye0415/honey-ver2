package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType

interface FoodRepository {
    suspend fun findCategories(): Result<List<CategoryType>>
    suspend fun findAllCategoryMenus(): Result<List<Category>>
}
