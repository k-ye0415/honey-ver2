package com.jin.honey.feature.firestore

import com.jin.honey.feature.food.domain.model.Category

interface FireStoreDataSource {
    suspend fun fetchFoodList():List<Category>
}
