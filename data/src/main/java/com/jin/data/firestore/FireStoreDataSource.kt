package com.jin.data.firestore

import com.jin.domain.food.model.Food
import com.jin.domain.model.recipe.Recipe
import com.jin.domain.model.review.Review

interface FireStoreDataSource {
    suspend fun fetchAllCategoriesWithMenus(): Result<List<Food>>
    suspend fun fetchAllReviewWithMenus(docName: String): Result<List<Review>>
    suspend fun fetchAllRecipeWithMenus(): Result<List<Recipe>>
}
