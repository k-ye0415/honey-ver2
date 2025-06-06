package com.jin.honey.feature.district.domain.model

data class District(
    val placeName: String,
    val address: Address,
    val coordinate: Coordinate
)

data class Address(
    val lotNumAddress: String,
    val roadAddress: String,
)

data class Coordinate(
    val x: Double,
    val y: Double,
)
