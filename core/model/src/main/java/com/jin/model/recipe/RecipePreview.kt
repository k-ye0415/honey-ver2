package com.jin.model.recipe

import com.jin.model.food.CategoryType

data class RecipePreview(
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val recipe: Recipe
)
