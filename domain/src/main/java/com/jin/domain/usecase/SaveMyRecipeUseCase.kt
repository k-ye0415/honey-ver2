package com.jin.domain.usecase

import com.jin.domain.recipe.RecipeRepository
import com.jin.domain.recipe.model.Recipe

class SaveMyRecipeUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.saveMyRecipe(recipe)
    }
}
