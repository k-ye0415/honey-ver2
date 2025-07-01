package com.jin.domain.usecase

import com.jin.domain.cart.CartRepository
import com.jin.domain.order.model.Order
import com.jin.domain.order.OrderRepository

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
