package com.jin.data.firestore

data class RecipeDto(
    val menuName: String,
    val cookingTime: String,
    val recipeSteps: List<RecipeStepDto>
)

data class RecipeStepDto(
    val step: Int,
    val title: String,
    val description: List<String>
)
