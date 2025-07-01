package com.jin.network

import com.google.gson.Gson
import okhttp3.OkHttpClient

object NetworkProvider {
    val gson = Gson()
    val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
}
