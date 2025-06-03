package com.jin.honey.feature.cart

import com.jin.honey.feature.food.domain.model.Ingredient
import java.time.Instant

data class IngredientCart(
    val addedCartInstant: Instant,
    val menuName: String,
    val ingredients: List<Ingredient>,
)
