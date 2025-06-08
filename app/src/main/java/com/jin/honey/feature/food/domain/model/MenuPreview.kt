package com.jin.honey.feature.food.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuPreview(
    val categoryName: CategoryType,
    val menuName: String,
    val menuImageUrl: String
) : Parcelable
