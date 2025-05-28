package com.jin.honey.feature.food.domain

import com.jin.honey.feature.food.domain.model.Menu

interface FoodRepository {
    suspend fun getFoodList(): List<Menu>
}
