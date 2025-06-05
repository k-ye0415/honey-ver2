package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.cart.domain.model.IngredientCart
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun saveCartItem(cart: Cart): Result<Unit>
    fun fetchUnorderedCartItems(): Flow<List<Cart>>
    suspend fun removeCartItem(cartItem: Cart, ingredient: IngredientCart)
    suspend fun changeQuantityOfCartItems(quantityMap: Map<CartKey, Int>): Result<Unit>
}
