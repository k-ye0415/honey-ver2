package com.jin.honey.feature.payment.domain

import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.district.domain.model.UserAddress
import java.time.Instant

/*
* 저장할 목록
* 0. 결제 시간
* 0. 결제 진행 (결제, 취소)
* 1. 주소
* 2. cart
* 3. 요청사항
* 4. 라이더 요청사항
* 5. 상품금액
* 6. 배달요금
* 7. 총 금액
* */
data class Payment(
    val id: Int?,
    val payInstant: Instant,
    val payState: PaymentState,
    val address: UserAddress,
    val cart: List<Cart>,
    val requirement: Requirement,
    val prices: PayPrice
)

enum class PaymentState(val state: String) {
    ORDER("order"), CANCEL("cancel")
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
