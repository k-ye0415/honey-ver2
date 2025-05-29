package com.jin.honey.feature.food.data

import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.model.CategoryEntity
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.unsplash.UnsplashDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepositoryImpl(
    private val db: FoodTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource,
    private val unsplashDataSource: UnsplashDataSource
) : FoodRepository {
    override suspend fun findAllCategoryMenus(): Result<List<Category>> =
        fireStoreDataSource.requestAllCategoryMenus()
            .onSuccess { insertOrUpdateAllCategoryMenus(it) }
            .map { Result.success(it) }
            .getOrElse { Result.failure(it) }

    private suspend fun insertOrUpdateAllCategoryMenus(list: List<Category>) {
        try {
            withContext(Dispatchers.IO) {
                val entities = list.flatMap { it.toEntityModel() }
                for (entity in entities){
                    db.insertOrUpdateCategory(entity)
                }
            }
        } catch (e: Exception) {
            // Silently ignore the error.
        }
    }


    private fun Category.toEntityModel(): List<CategoryEntity> {
        return menu.map {
            CategoryEntity(
                categoryName = categoryType.categoryName,
                menuName = it.name,
                imageUrl = it.imageUrl,
                ingredients = it.ingredients
            )
        }
    }
}
