package com.jin.honey.feature.food.domain.model

data class Menu(
    val name: String,
    val category: String,
    val ingredient: List<Ingredient>
)
