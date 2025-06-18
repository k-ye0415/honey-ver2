package com.jin.honey.feature.order.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.order.domain.model.Order

interface OrderRepository {
    suspend fun savePayAndOrder(order: Order): Result<Unit>
    suspend fun fetchOrderHistories(): List<Order>
    suspend fun fetchOrderPayment(orderKey: String): Order?
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): List<IngredientCart>
}
