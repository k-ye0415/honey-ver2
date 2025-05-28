package com.jin.honey.feature.food.data

import com.jin.honey.feature.food.domain.FoodRepository
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu

class FoodRepositoryImpl : FoodRepository {
    override fun getFoodList(): List<Menu> {
        // FIXME firebase firestore 연동 필요
        val burger = Menu(
            categoryType = CategoryType.Burger,
            name = "클래식 치즈버거",
            ingredient = listOf(
                Ingredient(
                    name = "햄버거 번",
                    quantity = "1개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "소고기 패티",
                    quantity = "100g",
                    unitPrice = 2000
                ),
                Ingredient(
                    name = "슬라이스 치즈",
                    quantity = "1장",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "양상추",
                    quantity = "1장",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "케첩/마요네즈",
                    quantity = "각 1큰술",
                    unitPrice = 1000
                ),
            )
        )
        val cafe = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Dessert,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        val chicken = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Chicken,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        val chinese = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Chinese,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        val japanese = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Japanese,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        val snack = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Snack,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        val vegan = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Vegan,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        val korean = Menu(
            name = "수플레 팬케이크",
            categoryType = CategoryType.Korean,
            ingredient = listOf(
                Ingredient(
                    name = "달걀",
                    quantity = "2개",
                    unitPrice = 1000
                ),
                Ingredient(
                    name = "설탕",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "밀가루",
                    quantity = "2큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "우유",
                    quantity = "1큰술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "베이킹파우더",
                    quantity = "1/4작은술",
                    unitPrice = 500
                ),
                Ingredient(
                    name = "바닐라 익스트랙",
                    quantity = "약간",
                    unitPrice = 500
                ),
            )
        )

        return listOf(burger, cafe, chicken, chinese, japanese, snack, vegan, korean)
    }
}
