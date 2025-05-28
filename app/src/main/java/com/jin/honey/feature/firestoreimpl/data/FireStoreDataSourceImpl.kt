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
    override suspend fun fetchFoodList(): List<Category> = coroutineScope {
//            val categorizedMenus = mapOf(
//                name to list,
//            )
//            val burgerDocRef = fireStore.collection("categories").document("name")
//
//            for ((categoryName, menus) in categorizedMenus) {
//                val menuCallection = burgerDocRef.collection(categoryName).document("menus")
//                menuCallection.set(mapOf("menus" to menus))
//            }

        val docNameList = listOf(
            "burger", "korean", "chicken", "chinese", "japanese", "snack", "vegan", "dessert",
        )
        val categoryList = mutableListOf<Category>()
        val categoriesRef = fireStore.collection("categories")
        val categoryDocs = categoriesRef.get().await()
        for (categoryDoc in categoryDocs) {
            docNameList.map { name ->
                val menusRef = categoryDoc.reference.collection(name)
                val menuDocs = menusRef.get().await()

                for (menuDoc in menuDocs) {
                    val json = NetworkProvider.gson.toJson(menuDoc.data)
                    Log.d("Firestore", "YEJIN menuDoc.json: $json")
                    val category = parsedJsonToCategory(name, json)
                    categoryList.add(category)
                }

            }
        }
        return@coroutineScope categoryList
    }

    private fun parsedJsonToCategory(docName: String, docJson: String): Category {
        val typeToken = object : TypeToken<Map<String, List<Menu>>>() {}.type
        val parsedMap: Map<String, List<Menu>> = NetworkProvider.gson.fromJson(docJson, typeToken)
        val menuList = parsedMap["menus"] ?: emptyList()
        return Category(CategoryType.findByFirebaseDoc(docName), menuList)
    }
}
