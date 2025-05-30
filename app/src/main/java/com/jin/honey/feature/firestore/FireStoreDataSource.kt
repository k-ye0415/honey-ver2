package com.jin.honey.feature.firestore

import com.jin.honey.feature.food.domain.model.Food

interface FireStoreDataSource {
    suspend fun fetchAllCategoriesWithMenus(): Result<List<Food>>
}
