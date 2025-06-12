package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.recipe.domain.model.RecipePreview

class GetRecipeUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String): Result<RecipePreview> {
        return repository.findRecipeByMenuName(menuName)
    }
}
