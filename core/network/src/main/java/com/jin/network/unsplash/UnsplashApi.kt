package com.jin.network.unsplash

import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos/random")
    suspend fun fetchPlantImageUrl(
        @Query("client_id") clientId: String,
        @Query("query") query: String,
        @Query("orientation") orientation: String,
        @Query("count") count: Int,
    ): List<UnsplashItem>
}
