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
                "버거", "burger" -> CategoryType.Burger
                "치킨", "chicken" -> CategoryType.Chicken
                "중식", "chinese" -> CategoryType.Chinese
                "일식", "japanese" -> CategoryType.Japanese
                "한식", "korean" -> CategoryType.Korean
                "분식", "snack" -> CategoryType.Snack
                "비건", "vegan" -> CategoryType.Vegan
                "카페/디저트", "dessert" -> CategoryType.Dessert
                else -> CategoryType.ERROR
            }
        }
    }
}
