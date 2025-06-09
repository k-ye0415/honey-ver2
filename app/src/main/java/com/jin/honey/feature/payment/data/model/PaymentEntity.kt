package com.jin.honey.feature.payment.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.honey.feature.cart.domain.model.Cart

@Entity(tableName = "payAndOrder")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val payDataTime: Long,
    val payState: String,
    val placeName: String,
    val lotNumberAddress: String,
    val roadAddress: String,
    val detailAddress: String,
    val cart: List<Cart>,
    val requirement: String,
    val riderRequirement: String,
    val productPrice: Int,
    val deliveryPrice: Int,
    val totalPrice: Int
)
