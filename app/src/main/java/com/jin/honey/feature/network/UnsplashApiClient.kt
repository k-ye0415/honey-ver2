package com.jin.honey.feature.network

import com.jin.honey.feature.unsplash.UnsplashApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UnsplashApiClient {
    fun createService(): UnsplashApi {
        val client = NetworkProvider.okHttpClient
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(NetworkProvider.gson))
            .build()
            .create(UnsplashApi::class.java)
    }
}
