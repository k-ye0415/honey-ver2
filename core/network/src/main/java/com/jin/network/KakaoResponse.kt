package com.jin.network

import com.google.gson.annotations.SerializedName

data class KakaoResponse<T>(
    val documents: List<T>
)

typealias KakaoAddressResponse = KakaoResponse<AddressDocument>
typealias KakaoKeywordResponse = KakaoResponse<KeywordDocument>

/**
 * 입력 텍스트가 주소인 경우 일치하는 주소 반환
 *
 * @property lotNumberAddress
 * @property roadAddressName
 * @property x
 * @property y
 */
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
 * 임력 텍스트가 건물이름인 경우 일치하는 이름과 주소 반환
 *
 * @property lotNumAddressName
 * @property placeName
 * @property roadAddressName
 * @property x
 * @property y
 */
data class KeywordDocument(
    @SerializedName("address_name") val lotNumAddressName: String,
    @SerializedName("place_name") val placeName: String,
    @SerializedName("road_address_name") val roadAddressName: String,
    val x: String, // 경도
    val y: String  // 위도
)
