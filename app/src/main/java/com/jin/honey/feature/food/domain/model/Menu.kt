package com.jin.honey.feature.food.domain.model

import androidx.annotation.DrawableRes
import com.jin.honey.R

data class Category(
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
    @DrawableRes val imageRes: Int
) {
    ERROR("ERROR", R.drawable.ic_launcher_foreground),
    Burger("버거", R.drawable.ic_burger),
    Chicken("치킨", R.drawable.ic_chicken),
    Chinese("중식", R.drawable.ic_chinese),
    Japanese("일식", R.drawable.ic_japanese),
    Korean("한식", R.drawable.ic_bibimbap),
    Snack("분식", R.drawable.ic_snack),
    Vegan("비건", R.drawable.ic_vegan),
    Dessert("카페/디저트", R.drawable.ic_dessert);

    companion object {
        fun findByFirebaseDoc(name: String): CategoryType {
            return when (name) {
                "burger" -> CategoryType.Burger
                "chicken" -> CategoryType.Chicken
                "chinese" -> CategoryType.Chinese
                "japanese" -> CategoryType.Japanese
                "korean" -> CategoryType.Korean
                "snack" -> CategoryType.Snack
                "vegan" -> CategoryType.Vegan
                "dessert" -> CategoryType.Dessert
                else -> CategoryType.ERROR
            }
        }
    }
}
