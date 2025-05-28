package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Category

class GetAllMenusUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<Category>> {
        return repository.findAllCategoryMenus()
    }
}
