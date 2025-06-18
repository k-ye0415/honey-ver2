package com.jin.honey.feature.address.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val addressType: String,
    val placeName: String,
    val lotNumberAddress: String,
    val roadAddress: String,
    val detailAddress: String,
    val coordinateX: Double,
    val coordinateY: Double,
)
