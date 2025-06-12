package com.jin.honey.feature.firestore

import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.review.domain.Review

interface FireStoreDataSource {
    suspend fun fetchAllCategoriesWithMenus(): Result<List<Food>>
    suspend fun fetchAllReviewWithMenus(docName: String): Result<List<Review>>
}
