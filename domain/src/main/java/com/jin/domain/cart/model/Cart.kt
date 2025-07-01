package com.jin.domain.cart.model

import com.jin.domain.food.model.CategoryType
import java.time.Instant

data class Cart(
    val id: Int?,
    val addedCartInstant: Instant,
    val categoryType: CategoryType,
    val menuName: String,
    val menuImageUrl: String,
    val ingredients: List<IngredientCart>,
    val isOrdered: Boolean
)

data class IngredientCart(
    val name: String,
    val cartQuantity: Int,
    val quantity: String,
    val unitPrice: Int
)
