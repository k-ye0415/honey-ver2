package com.jin.honey.feature.firestore

interface FireStoreDataSource {
    suspend fun fetchFoodList()
}
