package com.jin.honey.feature.district.domain.model

data class District(
    val name: String,
    val address: Address,
    val coordinate: Coordinate
)

data class Address(
    val address: String,
    val roadAddress: String,
)

data class Coordinate(
    val x: Long,
    val y: Long,
)
