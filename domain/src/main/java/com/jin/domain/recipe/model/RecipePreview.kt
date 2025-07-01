package com.jin.domain.recipe.model

import com.jin.domain.food.model.CategoryType

data class RecipePreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val recipe: Recipe
)
