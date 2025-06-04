package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.ingredient.ui.model.IngredientPreview

class GetIngredientUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String): Result<IngredientPreview> {
        return repository.findIngredientByMenuName(menuName)
    }
}
