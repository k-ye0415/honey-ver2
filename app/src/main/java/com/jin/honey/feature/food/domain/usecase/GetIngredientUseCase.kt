package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.ingredient.model.IngredientPreview

class GetIngredientUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String): Result<IngredientPreview> {
        val ingredient = repository.findIngredientByMenuName(menuName)
        return if (ingredient == null) Result.failure(Exception("Ingredient is null")) else Result.success(ingredient)
    }
}
