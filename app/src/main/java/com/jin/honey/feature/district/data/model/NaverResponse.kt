package com.jin.honey.feature.district.data.model

import com.google.gson.annotations.SerializedName

data class NaverResponse(
    @SerializedName("items") val items: List<AddressItem>
)

data class AddressItem(
    val title: String,
    val address: String,
    val roadAddress: String,
    val mapx: String,
    val mapy: String
)
