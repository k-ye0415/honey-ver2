package com.jin.domain.model.recipe

import com.jin.domain.food.model.CategoryType

data class RecipePreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val recipe: Recipe
)
