package com.jin.honey.feature.firestore

import com.jin.model.food.Food
import com.jin.model.recipe.Recipe
import com.jin.model.review.Review

interface FireStoreDataSource {
    suspend fun fetchAllCategoriesWithMenus(): Result<List<Food>>
    suspend fun fetchAllReviewWithMenus(docName: String): Result<List<Review>>
    suspend fun fetchAllRecipeWithMenus(): Result<List<Recipe>>
}
