package com.jin.domain.model.food

data class IngredientPreview(
    val categoryType: com.jin.domain.model.food.CategoryType,
    val menuName:String,
    val imageUrl:String,
    val ingredients:List<com.jin.domain.model.food.Ingredient>
)
