package com.jin.domain.order.model

import com.jin.domain.address.model.Address
import com.jin.domain.cart.model.Cart
import java.time.Instant

data class Order(
    val id: Int?,
    val orderKey: String,
    val payInstant: Instant,
    val payState: PaymentState,
    val address: Address,
    val cart: List<Cart>,
    val requirement: Requirement,
    val prices: PayPrice
)

enum class PaymentState(val state: String) {
    ORDER("order"), CANCEL("cancel");

    companion object {
        fun findByStateLabel(state: String): String {
            return when (state) {
                ORDER.state -> "주문 완료"
                CANCEL.state -> "주문 취소"
                else -> ""
            }
        }

        fun findByState(state: String): PaymentState {
            return when (state) {
                "order" -> ORDER
                else -> CANCEL
            }
        }
    }
}

data class Requirement(
    val requirement: String,
    val riderRequirement: String
)

data class PayPrice(
    val productPrice: Int,
    val deliveryPrice: Int,
    val totalPrice: Int
)
