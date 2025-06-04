package com.jin.honey.feature.recipe.model

import com.jin.honey.feature.food.domain.model.Recipe

data class RecipePreview(
    val menuName: String,
    val menuImageUrl: String,
    val recipe: Recipe
)
