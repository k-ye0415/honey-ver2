package com.jin.honey.feature.network

import com.jin.honey.BuildConfig
import com.jin.honey.feature.address.data.KakaoMapApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoMapApiClient {
    fun createService(): KakaoMapApi {
        val client = NetworkProvider.okHttpClient
            .newBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_MAP_AK}")
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
