package com.jin.honey.feature.ingredient.model

import com.jin.honey.feature.food.domain.model.Ingredient

data class IngredientPreview(
    val menuName:String,
    val imageUrl:String,
    val ingredients:List<Ingredient>
)
