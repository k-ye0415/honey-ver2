package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.Menu

class GetAllFoodUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): List<Category> {
        return repository.getFoodList()
    }
}
