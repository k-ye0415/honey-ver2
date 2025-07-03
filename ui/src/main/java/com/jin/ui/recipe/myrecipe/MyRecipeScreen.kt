package com.jin.ui.recipe.myrecipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.recipe.model.RecipeStep
import com.jin.domain.recipe.model.RecipeType
import com.jin.ui.recipe.myrecipe.content.MyRecipeHeader
import com.jin.ui.recipe.myrecipe.content.MyRecipeSaveButton
import com.jin.ui.recipe.myrecipe.content.MyRecipeSteps

@Composable
fun MyRecipeScreen(viewModel: MyRecipeViewModel, menuName: String, onNavigateToBackStack: () -> Unit) {
    var recipeStepList by remember {
        mutableStateOf(
            listOf(
                RecipeStep(
                    step = 1,
                    title = "",
                    description = listOf("")
                )
            )
        )
    }
    var cookTimeHourKeyword by remember { mutableStateOf("") }
    var cookTimeMinKeyword by remember { mutableStateOf("") }
    var stepTitleKeywords by remember { mutableStateOf(listOf("")) }
    var stepDescKeywords by remember { mutableStateOf(listOf(listOf(""))) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyRecipeHeader(menuName, onNavigateToBackStack)
            MyRecipeSteps(
                modifier = Modifier.weight(1f),
                recipeStepList = recipeStepList,
                cookTimeHourKeyword = cookTimeHourKeyword,
                cookTimeMinKeyword = cookTimeMinKeyword,
                onHourValueChange = { cookTimeHourKeyword = it },
                onMinValueChange = { cookTimeMinKeyword = it },
                stepTitleKeywords = stepTitleKeywords,
                onTitleChange = { listIndex, newTitle ->
                    stepTitleKeywords = stepTitleKeywords.toMutableList().apply {
                        set(listIndex, newTitle)
                    }
                },
                stepDescKeywords = stepDescKeywords,
                onDescriptionChange = { listIndex, descriptionIndex, newDesc ->
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val update = targetStep.toMutableList().apply {
                            set(descriptionIndex, newDesc)
                        }
                        set(listIndex, update)
                    }
                },
                onAddRecipeDescription = { listIndex, descriptionIndex ->
                    recipeStepList = recipeStepList.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val updatedDescriptions = targetStep.description.toMutableList().apply {
                            add(descriptionIndex + 1, "")
                        }
                        val updatedStep = targetStep.copy(description = updatedDescriptions)
                        set(listIndex, updatedStep)
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val update = targetStep.toMutableList().apply {
                            add("")
                        }
                        set(listIndex, update)
                    }
                },
                onRemoveRecipeDescription = { listIndex, descriptionIndex ->
                    recipeStepList = recipeStepList.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val currentDescriptions = targetStep.description

                        if (currentDescriptions.size <= 1) return@apply

                        val updatedDescriptions = currentDescriptions.toMutableList().apply {
                            removeAt(descriptionIndex)
                        }
                        val updatedStep = targetStep.copy(description = updatedDescriptions)
                        set(listIndex, updatedStep)
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        val targetStep = get(listIndex)
                        val update = targetStep.toMutableList().apply {
                            removeAt(descriptionIndex)
                        }
                        set(listIndex, update)
                    }
                },
                onAddRecipeStep = {
                    recipeStepList = recipeStepList.toMutableList().apply {
                        add(
                            RecipeStep(
                                step = recipeStepList.size + 1,
                                title = "",
                                description = listOf("")
                            )
                        )
                    }
                    stepTitleKeywords = stepTitleKeywords.toMutableList().apply {
                        add("")
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        add(listOf(""))
                    }
                },
                onRemoveRecipeStep = {
                    recipeStepList = recipeStepList.toMutableList().apply {
                        removeAt(it)
                    }
                    stepTitleKeywords = stepTitleKeywords.toMutableList().apply {
                        removeAt(it)
                    }
                    stepDescKeywords = stepDescKeywords.toMutableList().apply {
                        removeAt(it)
                    }
                }
            )
            MyRecipeSaveButton(
                onSaveMyRecipe = {
                    val recipeSteps = mutableListOf<RecipeStep>()
                    for (index in recipeStepList.indices) {
                        val recipeStep = RecipeStep(
                            step = index + 1,
                            title = stepTitleKeywords[index],
                            description = stepDescKeywords[index]
                        )
                        recipeSteps.add(recipeStep)
                    }
                    val recipe = Recipe(
                        type = RecipeType.MY_OWN,
                        menuName = menuName,
                        cookingTime = "${cookTimeHourKeyword}시간 ${cookTimeMinKeyword}분",
                        recipeSteps = recipeSteps
                    )
                    viewModel.saveMyRecipe(recipe)
                }
            )
        }
    }
}
