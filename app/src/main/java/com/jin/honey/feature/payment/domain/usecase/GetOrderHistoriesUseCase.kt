package com.jin.honey.feature.payment.domain.usecase

import com.jin.honey.feature.payment.domain.PaymentRepository
import com.jin.honey.feature.payment.domain.model.Payment

class GetOrderHistoriesUseCase(private val repository: PaymentRepository) {
    suspend operator fun invoke(): Result<List<Payment>> {
        val paymentHistory = repository.fetchOrderHistories()
        return if (paymentHistory.isEmpty()) Result.failure(Exception("Order history is empty"))
        else Result.success(paymentHistory)
    }
}
