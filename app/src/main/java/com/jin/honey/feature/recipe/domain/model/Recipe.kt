package com.jin.honey.feature.recipe.domain.model

data class Recipe(
    val menuName: String,
    val cookingTime: String,
    val recipeSteps: List<RecipeStep>,
)

data class RecipeStep(
    val step: Int,
    val title: String,
    val description: List<String>
)
