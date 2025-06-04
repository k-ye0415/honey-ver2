package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.Cart

interface CartRepository {
    suspend fun saveIngredientToCart(cart: Cart): Result<Unit>
}
