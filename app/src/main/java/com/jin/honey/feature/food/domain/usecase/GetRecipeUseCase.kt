package com.jin.honey.feature.food.domain.usecase

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.recipe.domain.model.RecipePreview

class GetRecipeUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(menuName: String): Result<RecipePreview> {
        val recipe = repository.findRecipeByMenuName(menuName)
        return if (recipe == null) Result.failure(Exception("Recipe is null")) else Result.success(recipe)
    }
}
