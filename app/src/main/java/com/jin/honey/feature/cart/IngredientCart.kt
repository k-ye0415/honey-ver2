package com.jin.honey.feature.cart

data class IngredientCart(
    val isSelected: Boolean,
    val menuName: String,
    val ingredientName: String,
    val quantity: Int,
    val totalPrice: Int
)
