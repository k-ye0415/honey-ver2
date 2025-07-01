package com.jin.network.kakao

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
