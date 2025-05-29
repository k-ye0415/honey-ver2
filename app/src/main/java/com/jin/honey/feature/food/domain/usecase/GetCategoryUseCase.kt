package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.CategoryType

class GetCategoryUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<CategoryType>> {
        return repository.findCategories()
    }
}
