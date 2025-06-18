package com.jin.honey.feature.recipe.domain.model

import com.jin.honey.feature.food.domain.model.CategoryType

data class RecipePreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val recipe: Recipe
)
