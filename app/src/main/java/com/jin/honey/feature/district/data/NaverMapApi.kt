package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.NaverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NaverMapApi {
    @GET("v1/search/local.json")
    suspend fun searchAddress(
        @Query("query") query: String,
        @Query("display") display: Int,
        @Query("start") start: Int,
        @Query("sort") sort: String
    ): Response<NaverResponse>
}
