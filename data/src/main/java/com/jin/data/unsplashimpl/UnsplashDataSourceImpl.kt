package com.jin.data.unsplashimpl

import com.jin.data.unsplash.UnsplashDataSource
import com.jin.network.unsplash.UnsplashApi

class UnsplashDataSourceImpl(private val unsplashApi: UnsplashApi) : UnsplashDataSource {
    override suspend fun queryFoodImage(apiKey: String, name: String): List<String> {
        val response = unsplashApi.fetchPlantImageUrl(
            clientId = apiKey,
            query = "chicken",
            orientation = "landscape",
            10
        )
        return response.map {
            it.urls.regular
        }
    }
}
