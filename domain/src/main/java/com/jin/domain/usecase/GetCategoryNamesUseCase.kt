package com.jin.domain.usecase

import com.jin.domain.repositories.FoodRepository

class GetCategoryNamesUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<String>> {
        val categoryNames = repository.findCategoryNames()
        return if (categoryNames.isEmpty()) Result.failure(Exception("Category name is empty"))
        else Result.success(categoryNames)
    }
}
