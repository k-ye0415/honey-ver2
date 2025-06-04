package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart

interface CartRepository {
    suspend fun saveIngredientToCart(cart: IngredientCart): Result<Unit>
}
