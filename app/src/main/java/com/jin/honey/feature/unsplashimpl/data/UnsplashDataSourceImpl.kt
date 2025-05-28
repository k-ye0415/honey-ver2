package com.jin.honey.feature.unsplashimpl.data

import com.jin.honey.BuildConfig
import com.jin.honey.feature.unsplash.UnsplashApi
import com.jin.honey.feature.unsplash.UnsplashDataSource

class UnsplashDataSourceImpl(private val unsplashApi: UnsplashApi) : UnsplashDataSource {
    override suspend fun queryFoodImage(name: String): List<String> {
        val response = unsplashApi.fetchPlantImageUrl(
            clientId = BuildConfig.UNSPLASH_API_KEY,
            query = "chicken",
            orientation = "landscape",
            10
        )
        return response.map {
            it.urls.regular
        }
    }
}
