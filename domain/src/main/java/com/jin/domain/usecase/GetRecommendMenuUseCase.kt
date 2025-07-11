package com.jin.domain.usecase

import com.jin.domain.food.FoodRepository
import com.jin.domain.food.model.MenuPreview

class GetRecommendMenuUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<MenuPreview>> {
        val randomMenus = repository.fetchRandomMenus()
        return if (randomMenus.isEmpty()) Result.failure(Exception("Random Menu list is empty"))
        else Result.success(randomMenus)
    }
}
