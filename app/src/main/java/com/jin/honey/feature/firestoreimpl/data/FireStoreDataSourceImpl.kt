package com.jin.honey.feature.firestoreimpl.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import com.jin.honey.feature.firestore.FireStoreDataSource
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await

class FireStoreDataSourceImpl(private val fireStore: FirebaseFirestore) : FireStoreDataSource {
    override suspend fun fetchFoodList() {
        coroutineScope {
//            val categoriesRef = fireStore.collection("categories")
//            val categoryDocs = categoriesRef.get().await()
//
//            categoryDocs.flatMap { categoryDoc ->
//                val menusRef = categoryDoc.reference.collection("menus")
//                val menuDocs = menusRef.get().await()
//
//                menuDocs.mapNotNull { menuDoc ->
//                    menuDoc.toObject(Menu::class.java)
//                }
//            }
            val burgerMenus = listOf(
                Menu(
                    name = "치즈버거",
                    ingredient = listOf(
                        Ingredient("소고기패티", "100g", 1300),
                        Ingredient("양상추", "1장", 500)
                    )
                ),
                Menu(
                    name = "불고기 버거",
                    ingredient = listOf(
                        Ingredient("햄버거 번", "1개", 500),
                        Ingredient("불고기 소스", "2큰술", 300)
                    )
                )
            )
            val koreanMenus = listOf(
                Menu(
                    name = "김치찌개",
                    ingredient = listOf(
                        Ingredient(name = "김치", quantity = "100g", unitPrice = 500),
                        Ingredient(name = "돼지고기", quantity = "50g", unitPrice = 500),
                    )
                )
            )
            val categorizedMenus = mapOf(
                "burger" to burgerMenus,
                "korean" to koreanMenus
            )
            val burgerDocRef = fireStore.collection("categories").document("name")

            for ((categoryName, menus) in categorizedMenus) {
                val menuCallection = burgerDocRef.collection(categoryName).document("menus")
                menuCallection.set(mapOf("menus" to menus))
            }
//            fireStore.collection("categories")
//                .document("name")
//                .collection("burger")
//                .document("menus")
//                .set(menu)  // 🔥 이 한 줄로 JSON 형태 그대로 들어감

            val testList = listOf("burger", "korean")
            val categoriesRef = fireStore.collection("categories")
            val categoryDocs = categoriesRef.get().await()
            for (categoryDoc in categoryDocs) {
//                val json = Gson().toJson(test)
//                Log.d("Firestore", "YEJIN json: $json") // ex. "Burger", "Korean" 등

                testList.map {
                    val menusRef = categoryDoc.reference.collection(it)
                    val menuDocs = menusRef.get().await()

                    for (menuDoc in menuDocs) {
                        Log.d("Firestore", "YEJIN Menu Document ID: ${menuDoc.id}")
                        Log.d("Firestore", "YEJIN Raw Data: ${menuDoc.data}")
                        val json = Gson().toJson(menuDoc.data)
                        Log.d("Firestore", "YEJIN menuDoc.json: $json")
                    }

                }
//                val menusRef = categoryDoc.reference.collection("burger")
//                val menuDocs = menusRef.get().await()
//
//                for (menuDoc in menuDocs) {
//                    Log.d("Firestore", "YEJIN Menu Document ID: ${menuDoc.id}")
//                    Log.d("Firestore", "YEJIN Raw Data: ${menuDoc.data}")
//                    val json = Gson().toJson(menuDoc.data)
//                    Log.d("Firestore", "YEJIN menuDoc.json: $json")
//                }
            }
        }
    }

    suspend fun fetchMenusByCategory() {

    }
}


val burgerMenus = listOf(
    Menu(
        name = "치즈버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("소고기패티", "100g", 2000),
            Ingredient("슬라이스 치즈", "1장", 500),
            Ingredient("양상추", "1장", 500),
            Ingredient("케첩/마요네즈", "각 1큰술", 500),
        )
    ),
    Menu(
        name = "불고기 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("불고기 소스", "2큰술", 500),
            Ingredient("소고기패티", "100g", 2000),
            Ingredient("양파", "30g", 500),
            Ingredient("마요네즈", "1큰술", 500),
        )
    ),
    Menu(
        name = "새우 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("새우패티", "1장", 2000),
            Ingredient("양상추", "1장", 500),
            Ingredient("타르타르소스", "1큰술", 500),
        )
    ),
    Menu(
        name = "베이컨 에그 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("베이컨", "2줄", 500),
            Ingredient("계란", "1개", 500),
            Ingredient("슬라이스 치즈", "1장", 500),
        )
    ),
    Menu(
        name = "치킨버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("치킨패티", "1장", 2000),
            Ingredient("양상추", "1장", 500),
            Ingredient("마요네즈", "1큰술", 500),
        )
    ),
    Menu(
        name = "머쉬룸 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("소고기패티", "100g", 2000),
            Ingredient("버섯볶음", "30g", 500),
            Ingredient("슬라이스 치즈", "1장", 500),
        )
    ),
    Menu(
        name = "트러플 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("소고기패티", "100g", 2000),
            Ingredient("트러플오일", "1작은술", 500),
            Ingredient("슬라이스 치즈", "1장", 500),
        )
    ),
    Menu(
        name = "오믈렛버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("달걀", "2개", 500),
            Ingredient("양파", "20g", 500),
            Ingredient("슬라이스 치즈", "1장", 500),
        )
    ),
    Menu(
        name = "바비큐 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("소고기패티", "100g", 2000),
            Ingredient("바비큐 소스", "1큰술", 500),
            Ingredient("양파", "30g", 500),
        )
    ),
    Menu(
        name = "바비큐 버거",
        ingredient = listOf(
            Ingredient("햄버거 번", "1개", 1000),
            Ingredient("치킨패티", "1장", 2000),
            Ingredient("페스토 소스", "1큰술", 500),
            Ingredient("루꼴라", "30g", 500),
        )
    ),
)
val dessertMenus = listOf(
    Menu(
        name = "수플레 팬케이크",
        ingredient = listOf(
            Ingredient(name = "달걀", quantity = "2개", unitPrice = 500),
            Ingredient(name = "설탕", quantity = "1큰술", unitPrice = 500),
            Ingredient(name = "밀가루", quantity = "2큰술", unitPrice = 500),
            Ingredient(name = "우유", quantity = "1큰술", unitPrice = 500),
            Ingredient(name = "베이킹파우더", quantity = "1/4작은술", unitPrice = 500),
            Ingredient(name = "바닐라 익스트랙", quantity = "약간", unitPrice = 500),
        )
    ),
    Menu(
        name = "크렘 브륄레",
        ingredient = listOf(
            Ingredient(name = "생크림", quantity = "200ml", unitPrice = 1000),
            Ingredient(name = "달걀노른자", quantity = "2개", unitPrice = 500),
            Ingredient(name = "설탕", quantity = "2큰술", unitPrice = 500),
            Ingredient(name = "바닐라 익스트랙", quantity = "약간", unitPrice = 500),
        )
    ),
    Menu(
        name = "딸기 생크림 케이크",
        ingredient = listOf(
            Ingredient(name = "박력분", quantity = "50g", unitPrice = 500),
            Ingredient(name = "달걀", quantity = "2개", unitPrice = 500),
            Ingredient(name = "설탕", quantity = "40g", unitPrice = 500),
            Ingredient(name = "생크림", quantity = "150ml", unitPrice = 1000),
            Ingredient(name = "딸기", quantity = "5~6개", unitPrice = 1000),
            Ingredient(name = "딸기", quantity = "5~6개", unitPrice = 1000),
        )
    ),
    Menu(
        name = "바스크 치즈케이크",
        ingredient = listOf(
            Ingredient(name = "크림치즈", quantity = "200g", unitPrice = 2000),
            Ingredient(name = "설탕", quantity = "40g", unitPrice = 500),
            Ingredient(name = "달걀", quantity = "1개", unitPrice = 500),
            Ingredient(name = "생크림", quantity = "100ml", unitPrice = 1000),
            Ingredient(name = "박력분", quantity = "1큰술", unitPrice = 1000),
        )
    ),
    Menu(
        name = "브라우니",
        ingredient = listOf(
            Ingredient(name = "다크초콜릿", quantity = "60g", unitPrice = 1000),
            Ingredient(name = "버터", quantity = "50g", unitPrice = 500),
            Ingredient(name = "설탕", quantity = "30g", unitPrice = 500),
            Ingredient(name = "달걀", quantity = "1개", unitPrice = 1000),
            Ingredient(name = "박력분", quantity = "40g", unitPrice = 1000),
        )
    ),
    Menu(
        name = "마들렌",
        ingredient = listOf(
            Ingredient(name = "버터", quantity = "50g", unitPrice = 1000),
            Ingredient(name = "설탕", quantity = "30g", unitPrice = 500),
            Ingredient(name = "달걀", quantity = "1개", unitPrice = 500),
            Ingredient(name = "박력분", quantity = "40g", unitPrice = 1000),
            Ingredient(name = "베이킹파우더", quantity = "1/4작은술", unitPrice = 1000),
            Ingredient(name = "바닐라 익스트랙", quantity = "약간", unitPrice = 500),
        )
    ),
    Menu(
        name = "초콜릿 무스",
        ingredient = listOf(
            Ingredient(name = "다크초콜릿", quantity = "50g", unitPrice = 1000),
            Ingredient(name = "설탕", quantity = "30g", unitPrice = 500),
            Ingredient(name = "달걀", quantity = "1개", unitPrice = 500),
            Ingredient(name = "박력분", quantity = "40g", unitPrice = 1000),
            Ingredient(name = "베이킹파우더", quantity = "1/4작은술", unitPrice = 1000),
            Ingredient(name = "바닐라 익스트랙", quantity = "약간", unitPrice = 500),
        )
    ),
)
