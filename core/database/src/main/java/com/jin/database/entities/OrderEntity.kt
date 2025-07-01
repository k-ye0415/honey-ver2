package com.jin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jin.domain.address.model.Address
import com.jin.domain.cart.model.Cart

@Entity(tableName = "payAndOrder")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val orderKey: String,
    val payDataTime: Long,
    val payState: String,
    val address: Address,
    val cart: List<Cart>,
    val requirement: String,
    val riderRequirement: String,
    val productPrice: Int,
    val deliveryPrice: Int,
    val totalPrice: Int
)
