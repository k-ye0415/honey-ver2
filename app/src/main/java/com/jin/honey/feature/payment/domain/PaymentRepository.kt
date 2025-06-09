package com.jin.honey.feature.payment.domain

import com.jin.honey.feature.payment.domain.model.Payment

interface PaymentRepository {
    suspend fun savePayAndOrder(payment: Payment): Result<Unit>
    suspend fun fetchOrderHistory(): Result<List<Payment>>
}
