package com.jin.honey.feature.recipe.domain

import com.jin.honey.feature.recipe.domain.model.RecipePreview

class GetRecommendRecipeUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(): Result<List<RecipePreview>> {
        val recipes = repository.fetchRecommendRecipe()
        return if (recipes.isEmpty()) Result.failure(Exception("Recommend recipe is emtpy"))
        else Result.success(recipes)
    }
}
