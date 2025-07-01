package com.jin.honey.feature.cart.domain.usecase

import com.jin.domain.repositories.CartRepository
import com.jin.domain.model.cart.Cart

class AddIngredientToCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cart: Cart): Result<Unit> {
        return repository.addItemToCart(cart)
    }
}
