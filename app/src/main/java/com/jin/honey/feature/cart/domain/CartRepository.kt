package com.jin.honey.feature.cart.domain

import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun saveCartItem(cart: Cart): Result<Unit>
    fun fetchUnorderedCartItems(): Flow<List<Cart>>
    suspend fun removeCartItem(cartItem: Cart, ingredientName: String)
    suspend fun changeQuantityOfCartItems(quantityMap: Map<CartKey, Int>): Result<Unit>
}
