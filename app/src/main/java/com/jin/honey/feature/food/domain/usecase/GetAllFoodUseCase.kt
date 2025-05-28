package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Menu

class GetAllFoodUseCase(private val repository: FoodRepository) {
    operator fun invoke(): List<Menu> {
        return repository.getFoodList()
    }
}
