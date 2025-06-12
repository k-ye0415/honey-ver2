package com.jin.honey.feature.payment.domain

import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.honey.feature.payment.domain.model.Payment

interface PaymentRepository {
    suspend fun savePayAndOrder(payment: Payment): Result<Unit>
    suspend fun fetchOrderHistories(): Result<List<Payment>>
    suspend fun fetchOrderPayment(orderKey: String): Result<Payment>
    suspend fun fetchOrderIngredients(orderKey: String, menuName: String): Result<List<IngredientCart>>
}
