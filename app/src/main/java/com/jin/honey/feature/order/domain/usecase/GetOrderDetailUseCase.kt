package com.jin.honey.feature.order.domain.usecase

import com.jin.honey.feature.order.domain.OrderRepository
import com.jin.honey.feature.order.domain.model.Order

class GetOrderDetailUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(orderKey: String): Result<Order> {
        val order = repository.fetchOrderPayment(orderKey)
        return if (order == null) Result.failure(Exception("order is null"))
        else Result.success(order)
    }
}
