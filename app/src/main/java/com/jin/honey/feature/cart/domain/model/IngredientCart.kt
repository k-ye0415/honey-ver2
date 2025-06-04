package com.jin.honey.feature.cart.domain.model

import com.jin.honey.feature.food.domain.model.Ingredient
import java.time.Instant

data class IngredientCart(
    val id: Int?,
    val addedCartInstant: Instant,
    val menuName: String,
    val menuImageUrl: String,
    val ingredients: List<Ingredient>,
)
