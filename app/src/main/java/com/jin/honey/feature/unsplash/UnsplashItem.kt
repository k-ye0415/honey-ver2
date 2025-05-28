package com.jin.honey.feature.unsplash

import com.google.gson.annotations.SerializedName

data class UnsplashItem(
    @SerializedName("id") val id: String,
    @SerializedName("urls") val urls: UnsplashUrls
)

data class UnsplashUrls(
    @SerializedName("regular") val regular: String
)
