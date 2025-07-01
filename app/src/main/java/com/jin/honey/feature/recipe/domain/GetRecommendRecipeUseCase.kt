package com.jin.honey.feature.recipe.domain

import com.jin.domain.repositories.FoodRepository
import com.jin.domain.repositories.RecipeRepository
import com.jin.domain.model.recipe.RecipePreview

class GetRecommendRecipeUseCase(
    private val recipeRepository: RecipeRepository,
    private val foodRepository: FoodRepository
) {
    suspend operator fun invoke(): Result<List<RecipePreview>> {
        val recipePreviews = mutableListOf< RecipePreview>()
        val recipes = recipeRepository.fetchRecommendRecipe()
        for (recipe in recipes) {
            val menu = foodRepository.findMenuByMenuName(recipe.menuName)
            if (menu != null) {
                val preview =  RecipePreview(
                    categoryType = menu.type,
                    menuName = menu.menuName,
                    menuImageUrl = menu.menuImageUrl,
                    recipe = recipe
                )
                recipePreviews.add(preview)
            }
        }
        return if (recipePreviews.isEmpty()) Result.failure(Exception("Recommend recipe is emtpy"))
        else Result.success(recipePreviews)
    }
}
