package com.jin.honey.feature.order.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.honey.feature.address.domain.model.UserAddress
import com.jin.honey.feature.cart.domain.model.Cart

@Entity(tableName = "payAndOrder")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderKey: String,
    val payDataTime: Long,
    val payState: String,
    val address: UserAddress,
    val cart: List<Cart>,
    val requirement: String,
    val riderRequirement: String,
    val productPrice: Int,
    val deliveryPrice: Int,
    val totalPrice: Int
)
