package com.jin.honey.feature.order.domain.usecase

import com.jin.honey.feature.cart.domain.CartRepository
import com.jin.honey.feature.order.domain.model.Order
import com.jin.honey.feature.order.domain.OrderRepository

class PayAndOrderUseCase(private val orderRepository: OrderRepository, private val cartRepository: CartRepository) {
    suspend operator fun invoke(order: Order): Result<Unit> {
        val saveResult = orderRepository.savePayAndOrder(order)
        return if (saveResult.isSuccess) {
            cartRepository.updateOrderCartItem(order.cart)
            Result.success(Unit)
        } else {
            saveResult
        }
    }
}
