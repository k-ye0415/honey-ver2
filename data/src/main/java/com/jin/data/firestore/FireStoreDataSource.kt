package com.jin.data.firestore

import com.jin.domain.food.model.Food
import com.jin.domain.review.Review
import com.jin.domain.recipe.model.Recipe

interface FireStoreDataSource {
    suspend fun fetchAllCategoriesWithMenus(): Result<List<Food>>
    suspend fun fetchAllReviewWithMenus(docName: String): Result<List<Review>>
    suspend fun fetchAllRecipeWithMenus(): Result<List<Recipe>>
}
