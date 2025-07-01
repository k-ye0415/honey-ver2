package com.jin.domain.food.model

data class IngredientPreview(
    val categoryType: CategoryType,
    val menuName:String,
    val imageUrl:String,
    val ingredients:List<Ingredient>
)
