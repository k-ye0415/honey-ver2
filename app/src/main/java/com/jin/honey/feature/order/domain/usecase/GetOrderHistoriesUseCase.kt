package com.jin.honey.feature.order.domain.usecase

import com.jin.honey.feature.order.domain.OrderRepository
import com.jin.honey.feature.order.domain.model.Order

class GetOrderHistoriesUseCase(private val repository: OrderRepository) {
    suspend operator fun invoke(): Result<List<Order>> {
        return repository.fetchOrderHistories()
    }
}
