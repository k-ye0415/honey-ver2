package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.CartKey

class ChangeQuantityUseCase(private val repository: CartRepository) {
    suspend operator fun invoke(quantityMap: Map<CartKey, Int>): Result<Unit> {
        return repository.changeCartQuantity(quantityMap)
    }
}
