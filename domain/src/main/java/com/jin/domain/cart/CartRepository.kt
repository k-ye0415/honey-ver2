package com.jin.domain.cart

import com.jin.domain.cart.model.Cart
import com.jin.domain.cart.model.CartKey
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addItemToCart(cart: Cart): Result<Unit>
    fun fetchUnorderedCartItems(): Flow<List<Cart>>
    suspend fun removeIngredientInCartItem(cartItem: Cart, ingredientName: String)
    suspend fun changeQuantityOfCartItems(quantityMap: Map<CartKey, Int>): Result<Unit>
    suspend fun removeMenuInCartItem(cartItem: Cart)
    suspend fun updateOrderCartItem(cartItems: List<Cart>)
}
