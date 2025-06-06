package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.KakaoAddressResponse
import com.jin.honey.feature.district.data.model.KakaoKeywordResponse
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
