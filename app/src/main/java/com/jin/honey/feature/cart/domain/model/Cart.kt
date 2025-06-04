package com.jin.honey.feature.cart.domain.model

import java.time.Instant

data class Cart(
    val addedCartInstant: Instant,
    val menuName: String,
    val ingredients: List<IngredientCart>,
)

data class IngredientCart(
    val name: String,
    val cartQuantity: Int,
    val quantity: String,
    val unitPrice: Int
)
