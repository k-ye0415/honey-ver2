package com.jin.honey.feature.location

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapApi {
    @GET("v1/search/local.json")
    suspend fun searchAddress(
        @Query("query") query: String,
        @Query("display") display: Int = 10
    ): Response<NaverSearchResponse>
}


data class NaverSearchResponse(
    @SerializedName("items") val items: List<NaverAddressItem>
)

data class NaverAddressItem(
    val title: String,   // 예: <b>서울특별시 강남구</b>
    val address: String,
    val roadAddress: String,
    val mapx: String,
    val mapy: String
)
