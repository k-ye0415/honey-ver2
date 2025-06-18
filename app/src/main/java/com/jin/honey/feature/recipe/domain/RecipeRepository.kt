package com.jin.honey.feature.recipe.domain

import com.jin.honey.feature.recipe.domain.model.RecipePreview

interface RecipeRepository {
    suspend fun syncRecipe()
    suspend fun fetchRecommendRecipe(): List<RecipePreview>
}
