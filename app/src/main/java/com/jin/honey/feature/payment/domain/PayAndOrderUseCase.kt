package com.jin.honey.feature.payment.domain

class PayAndOrderUseCase(private val repository: PaymentRepository) {
    suspend operator fun invoke(payment: Payment) {
        repository.savePayAndOrder(payment)
    }
}
