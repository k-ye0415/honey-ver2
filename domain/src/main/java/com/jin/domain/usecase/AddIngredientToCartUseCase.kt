package com.jin.domain.usecase

import com.jin.domain.cart.CartRepository
import com.jin.domain.cart.model.Cart

class AddIngredientToCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cart: Cart): Result<Unit> {
        return repository.addItemToCart(cart)
    }
}
