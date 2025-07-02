package com.jin.domain.recipe

import com.jin.domain.recipe.model.Recipe

interface RecipeRepository {
    suspend fun syncRecipe()
    suspend fun fetchRecommendRecipe(): List<Recipe>
    suspend fun findRecipeByMenuName(menuName: String): Recipe?
    suspend fun saveMyRecipe(recipe: Recipe)
}
