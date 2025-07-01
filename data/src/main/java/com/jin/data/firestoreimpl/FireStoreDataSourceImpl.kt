package com.jin.data.firestoreimpl

import android.util.Log
import com.google.common.reflect.TypeToken
import com.google.firebase.firestore.FirebaseFirestore
import com.jin.data.firestore.FireStoreDataSource
import com.jin.data.firestore.RecipeDto
import com.jin.data.firestore.RecipeStepDto
import com.jin.domain.food.model.CategoryType
import com.jin.domain.food.model.Food
import com.jin.domain.food.model.Menu
import com.jin.domain.model.recipe.Recipe
import com.jin.domain.model.recipe.RecipeStep
import com.jin.domain.model.recipe.RecipeType
import com.jin.domain.model.review.Review
import com.jin.domain.model.review.ReviewContent
import com.jin.network.NetworkProvider
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
                        val categoryName = reviewMap["categoryName"] as String
                        val reviewList = reviewMap["review"] as? List<Map<String, Any>>

                        reviewList?.forEach { singleReview ->
                            val dt = singleReview["dt"] as Long
                            val reviewKey = singleReview["reviewKey"] as String
                            val content = singleReview["content"] as String
                            val totalScore = singleReview["totalScore"] as Long
                            val tasteScore = singleReview["tasteScore"] as Long
                            val recipeScore = singleReview["recipeScore"] as Long
                            // FIXME Dto 분리필요
                            val review = Review(
                                id = null,
                                orderKey = "",
                                reviewKey = reviewKey,
                                reviewInstant = Instant.ofEpochMilli(dt),
                                categoryType = CategoryType.findByFirebaseDoc(categoryName),
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
    } catch (e: Exception) {
        Log.e(TAG, "Firestore fail")
        Result.failure(e)
    }

    override suspend fun fetchAllRecipeWithMenus(): Result<List<Recipe>> {
        return try {
            coroutineScope {
                val recipes = mutableListOf<Recipe>()
                val categoriesRef = fireStore.collection(COLLECTION_NAME)
                val categoryDocs = categoriesRef.get().await()
                for (categoryName in DOCUMENT_NAME_LIST) {
                    val recipeRef = categoryDocs.find { it.id == "recipe" }?.reference?.collection(categoryName)
                        ?: return@coroutineScope Result.failure(Exception("Not found Document"))
                    val recipeDocs =
                        recipeRef.get().await() ?: return@coroutineScope Result.failure(Exception("Not found Document"))

                    for (recipe in recipeDocs) {
                        val json = com.jin.network.NetworkProvider.gson.toJson(recipe.data)
                        val recipesDto = parseRecipeFromJson(json)
                        for (dto in recipesDto) {
                            recipes.add(dto.toDomainModel())
                        }
                    }
                }
                Result.success(recipes)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun parseCategoryFromJson(docName: String, docJson: String): Food {
        val typeToken = object : TypeToken<Map<String, List<Menu>>>() {}.type
        val parsedMap: Map<String, List<Menu>> = com.jin.network.NetworkProvider.gson.fromJson(docJson, typeToken)
        val menuList = parsedMap[DOCUMENT_KEY_MENUS] ?: emptyList()
        return Food(CategoryType.findByFirebaseDoc(docName), menuList)
    }

    private fun parseRecipeFromJson(json: String): List<RecipeDto> {
        val typeToken = object : TypeToken<Map<String, List<RecipeDto>>>() {}.type
        val parsedMap: Map<String, List<RecipeDto>> = com.jin.network.NetworkProvider.gson.fromJson(json, typeToken)
        val recipeList = parsedMap[DOCUMENT_KEY_RECIPE] ?: emptyList()
        return recipeList
    }

    private fun RecipeDto.toDomainModel(): Recipe {
        return Recipe(
            type = RecipeType.DEFAULT,
            menuName = menuName,
            cookingTime = cookingTime,
            recipeSteps = recipeSteps.map { it.toDomainModel() }
        )
    }

    private fun RecipeStepDto.toDomainModel(): RecipeStep {
        return RecipeStep(step = step, title = title, description = description)
    }

    private companion object {
        val TAG = "FireStoreDataSource"
        val DOCUMENT_NAME_LIST = listOf(
            "burger", "korean", "western", "chinese", "japanese", "snack", "vegan", "dessert",
        )
        const val COLLECTION_NAME = "categories"
        const val DOCUMENT_KEY_MENUS = "menus"
        const val DOCUMENT_KEY_RECIPE = "recipes"
    }
}
