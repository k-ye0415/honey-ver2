package com.jin.honey.feature.district.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class District(
    val placeName: String,
    val address: Address,
    val coordinate: Coordinate
) : Parcelable

@Parcelize
data class Address(
    val lotNumAddress: String,
    val roadAddress: String,
) : Parcelable

@Parcelize
data class Coordinate(
    val x: Double,
    val y: Double,
) : Parcelable
