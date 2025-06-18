package com.jin.honey.feature.address.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchAddress(
    val placeName: String,
    val addressName: AddressName,
    val coordinate: Coordinate
) : Parcelable

@Parcelize
data class AddressName(
    val lotNumAddress: String,
    val roadAddress: String,
) : Parcelable

@Parcelize
data class Coordinate(
    val x: Double,
    val y: Double,
) : Parcelable
