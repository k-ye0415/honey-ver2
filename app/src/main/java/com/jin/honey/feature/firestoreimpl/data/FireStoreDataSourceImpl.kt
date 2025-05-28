package com.jin.honey.feature.firestoreimpl.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.reflect.TypeToken
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.network.NetworkProvider
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FireStoreDataSourceImpl(private val fireStore: FirebaseFirestore) : FireStoreDataSource {
    override suspend fun requestAllCategoryMenus(): Result<List<Category>> = try {
        coroutineScope {
//            val categorizedMenus = mapOf(
//                name to list,
//            )
//            val burgerDocRef = fireStore.collection("categories").document("name")
//
//            for ((categoryName, menus) in categorizedMenus) {
//                val menuCallection = burgerDocRef.collection(categoryName).document("menus")
//                menuCallection.set(mapOf("menus" to menus))
//            }


            val categoryList = mutableListOf<Category>()
            val categoriesRef = fireStore.collection(COLLECTION_NAME)
            val categoryDocs = categoriesRef.get().await()
            DOCUMENT_NAME_LIST.map { categoryName ->
                val menusRef = categoryDocs.first().reference.collection(categoryName)
                val menuDocs = menusRef.get().await()

                for (menuDoc in menuDocs) {
                    val json = NetworkProvider.gson.toJson(menuDoc.data)
                    val category = parseCategoryFromJson(categoryName, json)
                    categoryList.add(category)
                }

            }
            return@coroutineScope Result.success(categoryList)
        }
    } catch (e: Exception) {
        Log.e(TAG, "Firestore fail")
        Result.failure(e)
    }

    private fun parseCategoryFromJson(docName: String, docJson: String): Category {
        val typeToken = object : TypeToken<Map<String, List<Menu>>>() {}.type
        val parsedMap: Map<String, List<Menu>> = NetworkProvider.gson.fromJson(docJson, typeToken)
        val menuList = parsedMap[DOCUMENT_KEY_MENUS] ?: emptyList()
        return Category(CategoryType.findByFirebaseDoc(docName), menuList)
    }

    private companion object {
        val TAG = "FireStoreDataSource"
        val DOCUMENT_NAME_LIST = listOf(
            "burger", "korean", "chicken", "chinese", "japanese", "snack", "vegan", "dessert",
        )
        const val COLLECTION_NAME = "categories"
        const val DOCUMENT_KEY_MENUS = "menus"
    }
}
