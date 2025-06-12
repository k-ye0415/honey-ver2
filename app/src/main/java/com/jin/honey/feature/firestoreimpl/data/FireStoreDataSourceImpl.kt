package com.jin.honey.feature.firestoreimpl.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.reflect.TypeToken
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.network.NetworkProvider
import com.jin.honey.feature.review.domain.Review
import com.jin.honey.feature.review.domain.ReviewContent
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import java.time.Instant

class FireStoreDataSourceImpl(private val fireStore: FirebaseFirestore) : FireStoreDataSource {
    override suspend fun fetchAllCategoriesWithMenus(): Result<List<Food>> = try {
        coroutineScope {
            val foodList = mutableListOf<Food>()
            val categoriesRef = fireStore.collection(COLLECTION_NAME)
            val categoryDocs = categoriesRef.get().await()
            DOCUMENT_NAME_LIST.map { categoryName ->
                val menusRef = categoryDocs.first().reference.collection(categoryName)
                val menuDocs = menusRef.get().await()

                for (menuDoc in menuDocs) {
                    val json = NetworkProvider.gson.toJson(menuDoc.data)
                    val category = parseCategoryFromJson(categoryName, json)
                    foodList.add(category)
                }

            }
            return@coroutineScope Result.success(foodList)
        }
    } catch (e: Exception) {
        Log.e(TAG, "Firestore fail")
        Result.failure(e)
    }

    override suspend fun fetchAllReviewWithMenus(docName: String): Result<List<Review>> = try {
        coroutineScope {
            val reviews = mutableListOf<Review>()
            val categoriesRef = fireStore.collection(COLLECTION_NAME)
            val categoryDocs = categoriesRef.get().await()

            for (docs in categoryDocs) {
                if (docs.id == "review") {
                    val reviewRef = docs.reference.collection(docName)
                    val reviewDocs = reviewRef.get().await()
                    val firstDoc = reviewDocs.firstOrNull()

                    val reviewsField = firstDoc?.get("reviews") as? List<Map<String, Any>>
                    reviewsField?.forEach { reviewMap ->
                        val menuName = reviewMap["menuName"] as String
                        val reviewList = reviewMap["review"] as? List<Map<String, Any>>

                        reviewList?.forEach { singleReview ->
                            val dt = singleReview["dt"] as Long
                            val reviewKey = singleReview["reviewKey"] as String
                            val content = singleReview["content"] as String
                            val totalScore = singleReview["totalScore"] as Long
                            val tasteScore = singleReview["tasteScore"] as Long
                            val recipeScore = singleReview["recipeScore"] as Long
                            val review = Review(
                                id = null,
                                orderKey = "",
                                reviewKey = reviewKey,
                                reviewInstant = Instant.ofEpochMilli(dt),
                                menuName = menuName,
                                reviewContent = ReviewContent(
                                    reviewContent = content,
                                    totalScore = totalScore.toDouble(),
                                    tasteScore = tasteScore.toDouble(),
                                    recipeScore = recipeScore.toDouble()
                                )
                            )
                            reviews.add(review)
                        }
                    }
                }
            }
            return@coroutineScope Result.success(reviews)
        }
    } catch (e:Exception) {
        Log.e(TAG, "Firestore fail")
        Result.failure(e)
    }

    private fun parseCategoryFromJson(docName: String, docJson: String): Food {
        val typeToken = object : TypeToken<Map<String, List<Menu>>>() {}.type
        val parsedMap: Map<String, List<Menu>> = NetworkProvider.gson.fromJson(docJson, typeToken)
        val menuList = parsedMap[DOCUMENT_KEY_MENUS] ?: emptyList()
        return Food(CategoryType.findByFirebaseDoc(docName), menuList)
    }

    private companion object {
        val TAG = "FireStoreDataSource"
        val DOCUMENT_NAME_LIST = listOf(
            "burger", "korean", "western", "chinese", "japanese", "snack", "vegan", "dessert",
        )
        const val COLLECTION_NAME = "categories"
        const val DOCUMENT_KEY_MENUS = "menus"
    }
}
