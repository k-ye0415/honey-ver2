package com.jin.honey.feature.food.data

import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.data.model.CategoryEntity
import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodRepositoryImpl(
    private val db: FoodTrackingDataSource,
    private val fireStoreDataSource: FireStoreDataSource
) : FoodRepository {

    override suspend fun findCategories(): Result<List<CategoryType>> {
        return try {
            withContext(Dispatchers.IO) {
                val nameList = db.getCategoryNames()
                val categoryTypes = nameList.map {
                    CategoryType.findByFirebaseDoc(it)
                }.toSet().toList()
                Result.success(categoryTypes)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

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
                ingredients = it.ingredient
            )
        }
    }

    private companion object {
        val TAG = "FoodRepository"
    }
}
