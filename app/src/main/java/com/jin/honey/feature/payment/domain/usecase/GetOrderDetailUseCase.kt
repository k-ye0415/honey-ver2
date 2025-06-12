package com.jin.honey.feature.payment.domain.usecase

import com.jin.honey.feature.payment.domain.PaymentRepository
import com.jin.honey.feature.payment.domain.model.Payment

class GetOrderDetailUseCase(private val repository: PaymentRepository) {
    suspend operator fun invoke(orderKey: String): Result<Payment> {
        return repository.fetchOrderPayment(orderKey)
    }
}
