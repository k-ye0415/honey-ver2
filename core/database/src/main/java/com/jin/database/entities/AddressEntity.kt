package com.jin.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val isLatestAddress: Boolean,
    val addressType: String,
    val placeName: String,
    val lotNumberAddress: String,
    val roadAddress: String,
    val detailAddress: String,
    val coordinateX: Double,
    val coordinateY: Double,
)
