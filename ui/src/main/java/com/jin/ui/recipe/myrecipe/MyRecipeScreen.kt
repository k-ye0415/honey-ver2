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
import androidx.compose.ui.tooling.preview.Preview
import com.jin.domain.recipe.model.RecipeStep
import com.jin.ui.recipe.myrecipe.content.MyRecipeHeader
import com.jin.ui.recipe.myrecipe.content.MyRecipeSaveButton
import com.jin.ui.recipe.myrecipe.content.MyRecipeSteps
import com.jin.ui.theme.HoneyTheme

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
    var cookTimeHour by remember { mutableStateOf("") }
    var cookTimeMin by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyRecipeHeader(menuName, onNavigateToBackStack)
            MyRecipeSteps(
                modifier = Modifier.weight(1f),
                recipeStepList = recipeStepList,
                cookingTimeHour = cookTimeHour,
                cookingTimeMin = cookTimeMin,
                onHourValueChange = { cookTimeHour = it },
                onMinValueChange = { cookTimeMin = it },
                onAddRecipeDescription = { listIndex, descriptionIndex ->
                    recipeStepList = recipeStepList.toMutableList().apply {
                        val updatedDescription = get(listIndex).copy(
                            description = get(descriptionIndex).description + ""
                        )
                        set(listIndex, updatedDescription)
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
                },
                onRemoveRecipeStep = {
                    recipeStepList = recipeStepList.toMutableList().apply {
                        removeAt(it)
                    }
                }
            )
            MyRecipeSaveButton()
        }
    }
}
