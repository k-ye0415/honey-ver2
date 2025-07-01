package com.jin.honey.feature.cart.domain.usecase

import com.jin.domain.repositories.CartRepository
import com.jin.domain.model.cart.Cart
import kotlinx.coroutines.flow.Flow

class GetCartItemsUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<List<Cart>> = cartRepository.fetchUnorderedCartItems()
}
