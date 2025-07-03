package com.jin.ui.recipe.myrecipe

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import com.jin.domain.recipe.model.Recipe
import com.jin.domain.recipe.model.RecipeStep
import com.jin.domain.recipe.model.RecipeType
import com.jin.state.DbState
import com.jin.ui.recipe.myrecipe.content.MyRecipeHeader
import com.jin.ui.recipe.myrecipe.content.MyRecipeSaveButton
import com.jin.ui.recipe.myrecipe.content.MyRecipeSteps
import kotlinx.coroutines.launch

@Composable
fun MyRecipeScreen(viewModel: MyRecipeViewModel, menuName: String, onNavigateToBackStack: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
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

    val cookTimeHourFocusRequester = remember { FocusRequester() }
    val cookTimeMinFocusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        viewModel.insertState.collect {
            when (it) {
                is DbState.Success -> {
                    Toast.makeText(
                        context,
                        "레시피 저장 완료",
                        Toast.LENGTH_SHORT
                    ).show()
                    onNavigateToBackStack()
                }

                is DbState.Error -> Toast.makeText(
                    context,
                    "저장 실패. 다시 시도해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Scaffold(
        modifier = Modifier
        .fillMaxSize()
        .imePadding()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            MyRecipeHeader(menuName, onNavigateToBackStack)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                MyRecipeSteps(
                    cookTimeHourKeyword = cookTimeHourKeyword,
                    cookTimeHourFocusRequester = cookTimeHourFocusRequester,
                    cookTimeMinKeyword = cookTimeMinKeyword,
                    cookTimeMinFocusRequester = cookTimeMinFocusRequester,
                    recipeStepList = recipeStepList,
                    onHourValueChange = { cookTimeHourKeyword = it },
                    onMinValueChange = { cookTimeMinKeyword = it },
                    onTitleChange = { listIndex, newTitle ->
                        recipeStepList = recipeStepList.toMutableList().apply {
                            val updateTitle = get(listIndex).copy(title = newTitle)
                            set(listIndex, updateTitle)
                        }
                    },
                    onDescriptionChange = { listIndex, descriptionIndex, newDesc ->
                        recipeStepList = recipeStepList.toMutableList().apply {
                            val targetStep = get(listIndex)
                            val updatedDescriptions = targetStep.description.toMutableList().apply {
                                set(descriptionIndex, newDesc)
                            }
                            val updateStep = targetStep.copy(description = updatedDescriptions)
                            set(listIndex, updateStep)
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
                    },
                    onRemoveRecipeDescription = { listIndex, descriptionIndex ->
                        recipeStepList = recipeStepList.toMutableList().apply {
                            val targetStep = get(listIndex)
                            val currentDescriptions = targetStep.description
                            val updatedDescriptions = currentDescriptions.toMutableList().apply {
                                removeAt(descriptionIndex)
                            }
                            val updatedStep = targetStep.copy(description = updatedDescriptions)
                            set(listIndex, updatedStep)
                        }
                    },
                    onAddRecipeStep = {
                        recipeStepList = recipeStepList.toMutableList().apply {
                            add(
                                RecipeStep(
                                    step = 1,
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
            }
            MyRecipeSaveButton(
                onSaveMyRecipe = {
                    if (cookTimeMinKeyword.isEmpty()) {
                        Toast.makeText(context, "조리 시간을 입력해주세요", Toast.LENGTH_SHORT).show()
                        coroutineScope.launch {
                            cookTimeMinFocusRequester.requestFocus()
                        }
                        return@MyRecipeSaveButton
                    }
                    for (step in recipeStepList) {
                        if (step.title.isEmpty()) {
                            Toast.makeText(context, "조리 방법을 작성해주세요.", Toast.LENGTH_SHORT).show()
                            return@MyRecipeSaveButton
                        }

                        for (description in step.description) {
                            if (description.isEmpty()) {
                                Toast.makeText(context, "조리 상세 방법을 작성해주세요.", Toast.LENGTH_SHORT).show()
                                return@MyRecipeSaveButton
                            }
                        }
                    }
                    val recipe = Recipe(
                        type = RecipeType.MY_OWN,
                        menuName = menuName,
                        cookingTime = generateCookTime(cookTimeHourKeyword, cookTimeMinKeyword),
                        recipeSteps = recipeStepList.mapIndexed { index, recipeStep -> recipeStep.copy(step = index + 1) }
                    )
                    viewModel.saveMyRecipe(recipe)
                }
            )
        }
    }
}


private fun generateCookTime(hourKeyword: String, minKeyword: String): String {
    return when {
        hourKeyword.isEmpty() -> "${minKeyword}분"
        else -> "${hourKeyword}시간 ${minKeyword}분"
    }
}
