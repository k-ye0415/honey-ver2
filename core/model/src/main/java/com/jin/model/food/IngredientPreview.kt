package com.jin.model.food

data class IngredientPreview(
    val categoryType: CategoryType,
    val menuName:String,
    val imageUrl:String,
    val ingredients:List<Ingredient>
)
