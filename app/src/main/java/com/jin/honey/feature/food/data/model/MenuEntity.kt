package com.jin.honey.feature.food.data.model

import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.RecipeStep

data class MenuEntity(
    val menuName: String,
    val imageUrl: String,
    val cookingTime: String,
    val recipeStep: List<RecipeStep>,
    val ingredients: List<Ingredient>
)
