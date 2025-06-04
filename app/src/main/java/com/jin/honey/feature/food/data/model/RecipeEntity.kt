package com.jin.honey.feature.food.data.model

import com.jin.honey.feature.food.domain.model.RecipeStep

data class RecipeEntity(
    val menuName: String,
    val imageUrl: String,
    val cookingTime: String,
    val recipeStep: List<RecipeStep>,
)
