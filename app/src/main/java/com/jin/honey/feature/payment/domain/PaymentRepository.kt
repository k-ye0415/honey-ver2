package com.jin.honey.feature.payment.domain

import com.jin.honey.feature.payment.domain.model.Payment

interface PaymentRepository {
    suspend fun savePayAndOrder(payment: Payment): Result<Unit>
    suspend fun fetchOrderHistories(): Result<List<Payment>>
    suspend fun fetchOrderPaymentDetail(orderKey: String): Result<Payment>
}
