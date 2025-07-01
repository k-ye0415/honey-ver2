package com.jin.domain.usecase

import com.jin.domain.food.FoodRepository
import com.jin.domain.food.model.Food

class GetAllFoodsUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(): Result<List<Food>> {
        val foodList = repository.fetchAllFoodList()
        return if (foodList.isEmpty()) Result.failure(Exception("Food list is empty")) else Result.success(foodList)
    }
}
