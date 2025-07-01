package com.jin.domain

import com.jin.model.recipe.Recipe

interface RecipeRepository {
    suspend fun syncRecipe()
    suspend fun fetchRecommendRecipe(): List<Recipe>
    suspend fun findRecipeByMenuName(menuName: String): Recipe?
}
