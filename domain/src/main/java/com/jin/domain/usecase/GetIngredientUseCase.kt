package com.jin.domain.usecase

import com.jin.domain.repositories.FoodRepository

class GetIngredientUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String): Result<com.jin.domain.model.food.IngredientPreview> {
        val ingredient = repository.findIngredientByMenuName(menuName)
        return if (ingredient == null) Result.failure(Exception("Ingredient is null")) else Result.success(ingredient)
    }
}
