package com.jin.domain.food.model

data class Food(
    val categoryType: CategoryType,
    val menu: List<Menu>
)

data class Menu(
    val name: String,
    val imageUrl: String,
    val ingredient: List<Ingredient>
)

data class Ingredient(
    val name: String,
    val quantity: String,
    val unitPrice: Int
)

enum class CategoryType(val categoryName: String) {
    ERROR("ERROR"),
    Burger("버거"),
    Western("양식"),
    Chinese("중식"),
    Japanese("일식"),
    Korean("한식"),
    Snack("분식"),
    Vegan("비건"),
    Dessert("디저트");

    companion object {
        fun findByFirebaseDoc(name: String): CategoryType {
            return when (name) {
                "버거", "burger" -> Burger
                "양식", "western" -> Western
                "중식", "chinese" -> Chinese
                "일식", "japanese" -> Japanese
                "한식", "korean" -> Korean
                "분식", "snack" -> Snack
                "비건", "vegan" -> Vegan
                "디저트", "dessert" -> Dessert
                else -> ERROR
            }
        }
    }
}
