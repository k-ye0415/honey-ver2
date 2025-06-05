package com.jin.honey.feature.network

import com.jin.honey.BuildConfig
import com.jin.honey.feature.district.NaverMapApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NaverMapApiClient {
    fun createService(): NaverMapApi {
        val client = NetworkProvider.okHttpClient
            .newBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("X-Naver-Client-Id", BuildConfig.NAVER_MAP_CLIENT_ID)
                    .addHeader("X-Naver-Client-Secret", BuildConfig.NAVER_MAP_CLIENT_SECRET)
                    .build()
                chain.proceed(request)
            }.build()
        return Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(GsonConverterFactory.create(NetworkProvider.gson))
            .client(client)
            .build()
            .create(NaverMapApi::class.java)
    }
}
