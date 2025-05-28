package com.jin.honey.feature.unsplash

interface UnsplashDataSource {
    suspend fun queryFoodImage(name:String):List<String>
}
