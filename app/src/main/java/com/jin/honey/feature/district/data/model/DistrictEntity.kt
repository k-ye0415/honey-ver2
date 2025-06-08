package com.jin.honey.feature.district.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "district")
data class DistrictEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val districtType: String,
    val placeName: String,
    val lotNumberAddress: String,
    val roadAddress: String,
    val detailAddress: String,
    val coordinateX: Double,
    val coordinateY: Double,
)
