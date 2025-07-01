package com.jin.domain.usecase

import com.jin.domain.recipe.RecipeRepository

class SyncRecipesUseCase(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke() {
        recipeRepository.syncRecipe()
    }
}
