package com.jin.honey.feature.recipe.domain

class SyncRecipesUseCase(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke() {
        recipeRepository.syncRecipe()
    }
}
