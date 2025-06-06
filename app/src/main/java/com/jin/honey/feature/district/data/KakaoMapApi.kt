package com.jin.honey.feature.district.data

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KakaoMapApi {
    @GET("v2/local/search/address.json")
    suspend fun searchAddress(
        @Query("query") query: String
    ): Response<KakaoAddressResponse>

    @GET("v2/local/search/keyword.json")
    suspend fun searchKeyword(
        @Query("query") query: String
    ): Response<KakaoKeywordResponse>
}

/**
 * "방배중앙로 100"
 *
 * @property documents
 */
data class KakaoAddressResponse(
    val documents: List<AddressDocument>
)

data class AddressDocument(
    @SerializedName("address") val lotNumberAddress: LotNumberAddress?,
    @SerializedName("address_name") val roadAddressName: String,
    val x: String, // 경도
    val y: String  // 위도
)

data class LotNumberAddress(
    @SerializedName("address_name") val addressName: String
)

/**
 * "아크로리버"
 *
 */
data class KakaoKeywordResponse(
    val documents: List<KeywordDocument>
)

data class KeywordDocument(
    @SerializedName("address_name") val lotNumAddressName: String,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("road_address_name") val roadAddressName: String,
    val x: String, // 경도
    val y: String  // 위도
)
