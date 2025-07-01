package com.jin.domain.usecase

import com.jin.domain.cart.CartRepository
import com.jin.domain.cart.model.Cart

class RemoveIngredientInCartItemUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: Cart, ingredientName: String) {
        repository.removeIngredientInCartItem(cartItem, ingredientName)
    }
}
