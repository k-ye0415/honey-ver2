package com.jin.database.entities

import com.jin.domain.model.food.Ingredient

data class IngredientEntity(
    val categoryName: String,
    val menuName: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>
)
