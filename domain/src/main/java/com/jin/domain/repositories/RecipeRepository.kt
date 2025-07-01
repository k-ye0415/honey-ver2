package com.jin.domain.repositories

import com.jin.domain.model.recipe.Recipe

interface RecipeRepository {
    suspend fun syncRecipe()
    suspend fun fetchRecommendRecipe(): List<Recipe>
    suspend fun findRecipeByMenuName(menuName: String): Recipe?
}
