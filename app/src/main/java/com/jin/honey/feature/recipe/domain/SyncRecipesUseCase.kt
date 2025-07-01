package com.jin.honey.feature.recipe.domain

import com.jin.domain.repositories.RecipeRepository

class SyncRecipesUseCase(private val recipeRepository: RecipeRepository) {
    suspend operator fun invoke() {
        recipeRepository.syncRecipe()
    }
}
