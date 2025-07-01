package com.jin.domain.model.address

import java.io.Serializable

data class SearchAddress(
    val placeName: String,
    val addressName: AddressName,
    val coordinate: Coordinate
) : Serializable

data class AddressName(
    val lotNumAddress: String,
    val roadAddress: String,
): Serializable

data class Coordinate(
    val x: Double,
    val y: Double,
): Serializable
