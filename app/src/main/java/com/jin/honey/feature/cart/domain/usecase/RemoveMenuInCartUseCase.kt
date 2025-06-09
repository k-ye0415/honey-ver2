package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.Cart

class RemoveMenuInCartUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(cartItem: Cart) {
        repository.removeMenuInCartItem(cartItem)
    }
}
