package com.jin.model.food

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

enum class CategoryType(
    val categoryName: String,
    val imageResName: String
) {
    ERROR("ERROR", "ic_error"),
    Burger("버거", "ic_burger"),
    Western("양식", "ic_spaghetti"),
    Chinese("중식", "ic_chinese"),
    Japanese("일식", "ic_japanese"),
    Korean("한식", "ic_bibimbap"),
    Snack("분식", "ic_snack"),
    Vegan("비건", "ic_vegan"),
    Dessert("디저트", "ic_dessert");

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
