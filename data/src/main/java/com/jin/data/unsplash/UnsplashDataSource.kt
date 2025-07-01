package com.jin.data.unsplash

interface UnsplashDataSource {
    suspend fun queryFoodImage(apiKey: String, name:String):List<String>
}
