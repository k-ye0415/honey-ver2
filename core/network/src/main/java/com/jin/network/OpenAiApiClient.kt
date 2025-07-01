package com.jin.network

import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object OpenAiApiClient {
    fun createService(apiKey: String): OpenAiApi {
        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val authorizedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
            chain.proceed(authorizedRequest)
        }

        val client = NetworkProvider.okHttpClient
            .newBuilder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(NetworkProvider.gson))
            .build()
            .create(OpenAiApi::class.java)
    }
}
