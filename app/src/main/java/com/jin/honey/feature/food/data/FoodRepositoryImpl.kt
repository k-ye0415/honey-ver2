package com.jin.honey.feature.food.data

import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.unsplash.UnsplashDataSource

class FoodRepositoryImpl(
    private val fireStoreDataSource: FireStoreDataSource,
    private val unsplashDataSource: UnsplashDataSource
) : FoodRepository {
    override suspend fun findAllCategoryMenus(): Result<List<Category>> {
        // FIXME : RoomDB 저장 필요
        return fireStoreDataSource.requestAllCategoryMenus()
    }
}
