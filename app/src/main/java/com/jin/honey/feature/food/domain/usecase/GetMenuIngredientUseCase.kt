package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Menu

class GetMenuIngredientUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String): Result<Menu> {
        return repository.findMenuIngredient(menuName)
    }
}
