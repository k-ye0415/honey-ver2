package com.jin.honey.feature.payment.domain

interface PaymentRepository {
    suspend fun savePayAndOrder(payment: Payment): Result<Unit>
}
