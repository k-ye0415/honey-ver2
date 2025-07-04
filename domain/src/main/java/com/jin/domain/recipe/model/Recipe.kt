package com.jin.domain.recipe.model

data class Recipe(
    val type: RecipeType,
    val menuName: String,
    val cookingTime: String,
    val recipeSteps: List<RecipeStep>,
)

data class RecipeStep(
    val step: Int,
    val title: String,
    val description: List<String>
)

enum class RecipeType(val type: String) {
    DEFAULT("default"), MY_OWN("myOwn");

    companion object {
        fun findByTypName(name: String): RecipeType = if (name == DEFAULT.type) DEFAULT else MY_OWN
    }
}
