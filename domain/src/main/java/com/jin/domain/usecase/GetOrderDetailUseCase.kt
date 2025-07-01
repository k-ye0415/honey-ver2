package com.jin.domain.usecase

import com.jin.domain.order.OrderRepository
import com.jin.domain.order.model.Order

class GetOrderDetailUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(orderKey: String): Result<Order> {
        val order = repository.fetchOrderPayment(orderKey)
        return if (order == null) Result.failure(Exception("order is null"))
        else Result.success(order)
    }
}
