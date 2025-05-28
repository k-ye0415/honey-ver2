package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Category

interface FoodRepository {
    suspend fun getFoodList(): List<Category>
}
