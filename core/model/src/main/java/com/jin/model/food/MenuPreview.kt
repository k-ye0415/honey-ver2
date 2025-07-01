package com.jin.model.food

import java.io.Serializable

data class MenuPreview(
    val type: CategoryType,
    val menuName: String,
    val menuImageUrl: String
) : Serializable
