package com.jin.honey.feature.recipe.domain

import com.jin.honey.feature.recipe.domain.model.RecipePreview

class GetRecommendRecipeUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(): Result<List<RecipePreview>> {
        return repository.fetchRecommendRecipe()
    }
}
