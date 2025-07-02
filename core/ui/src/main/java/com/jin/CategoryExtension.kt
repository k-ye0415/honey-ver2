package com.jin

import androidx.annotation.DrawableRes
import com.jin.domain.food.model.CategoryType
import com.jin.honey.core.ui.R

@DrawableRes
fun CategoryType.drawableRes(): Int = when (this) {
    CategoryType.ERROR -> R.drawable.ic_error
    CategoryType.Burger -> R.drawable.ic_burger
    CategoryType.Western -> R.drawable.ic_spaghetti
    CategoryType.Chinese -> R.drawable.ic_chinese
    CategoryType.Japanese -> R.drawable.ic_japanese
    CategoryType.Korean -> R.drawable.ic_bibimbap
    CategoryType.Snack -> R.drawable.ic_snack
    CategoryType.Vegan -> R.drawable.ic_vegan
    CategoryType.Dessert -> R.drawable.ic_dessert
}
