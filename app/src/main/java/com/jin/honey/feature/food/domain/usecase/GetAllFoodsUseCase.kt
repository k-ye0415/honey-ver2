package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Food

class GetAllFoodsUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<Food>> {
        return repository.findAllCategoriesAndMenus()
    }
}
