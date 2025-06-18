package com.jin.honey.feature.payment.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.payment.domain.model.Payment

interface PaymentRepository {
    suspend fun savePayAndOrder(payment: Payment): Result<Unit>
    suspend fun fetchOrderHistories(): List<Payment>
    suspend fun findOrderPaymentByOrderKey(orderKey: String): Payment?
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): List<IngredientCart>
}
