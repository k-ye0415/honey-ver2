package com.jin.honey.feature.cart.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.cart.domain.model.IngredientCart
import kotlinx.coroutines.flow.Flow

class GetCartItemsUseCase(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<List<IngredientCart>> = cartRepository.fetchUnorderedCartItems()
}
