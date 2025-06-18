package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository

class GetCategoryNamesUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<String>> {
        val categoryNames = repository.findCategoryNames()
        return if (categoryNames.isEmpty()) Result.failure(Exception("Category name is empty"))
        else Result.success(categoryNames)
    }
}
