package com.jin.honey.feature.cart.domain.usecase

import com.jin.domain.CartRepository
import com.jin.model.cart.Cart

class RemoveMenuInCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: Cart) {
        repository.removeMenuInCartItem(cartItem)
    }
}
