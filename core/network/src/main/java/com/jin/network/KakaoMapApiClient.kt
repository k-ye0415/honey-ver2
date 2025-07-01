package com.jin.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoMapApiClient {
    fun createService(apiKey:String): KakaoMapApi {
        val client = NetworkProvider.okHttpClient
            .newBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK $apiKey")
                    .build()
                chain.proceed(request)
            }.build()

        return Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com/")
            .addConverterFactory(GsonConverterFactory.create(NetworkProvider.gson))
            .client(client)
            .build()
            .create(KakaoMapApi::class.java)
    }
}
