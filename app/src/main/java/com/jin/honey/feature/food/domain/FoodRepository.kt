package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Category

interface FoodRepository {
    suspend fun findAllCategoryMenus(): Result<List<Category>>
}
