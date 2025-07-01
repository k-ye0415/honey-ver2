package com.jin.honey.feature.order.domain

import com.jin.honey.feature.order.domain.model.Order
import com.jin.model.cart.IngredientCart

interface OrderRepository {
    suspend fun savePayAndOrder(order: Order): Result<Unit>
    suspend fun fetchOrderHistories(): List<Order>
    suspend fun fetchOrderPayment(orderKey: String): Order?
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): List<IngredientCart>
}
