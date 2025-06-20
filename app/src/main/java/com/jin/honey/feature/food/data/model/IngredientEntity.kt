package com.jin.honey.feature.food.data.model

import com.jin.honey.feature.food.domain.model.Ingredient

data class IngredientEntity(
    val categoryName: String,
    val menuName: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>
)
