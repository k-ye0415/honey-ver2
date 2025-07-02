package com.jin.ui.recipe.myrecipe.content

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.HoneyNumberField
import com.jin.HoneyTextField
import com.jin.RoundedBoxButton
import com.jin.domain.recipe.model.RecipeStep
import com.jin.ui.R
import com.jin.ui.theme.OrderDetailRequirementHintColor

@Composable
fun MyRecipeSteps(
    modifier: Modifier,
    cookingTimeHour: String,
    cookingTimeMin: String,
    recipeStepList: List<RecipeStep>,
    onHourValueChange: (newValue: String) -> Unit,
    onMinValueChange: (newValue: String) -> Unit,
    onAddRecipeDescription: (listIndex: Int, descriptionIndex: Int) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            RecipeCookTime(cookingTimeHour, cookingTimeMin, onHourValueChange, onMinValueChange)
        }
        items(recipeStepList.size) { index ->
            val item = recipeStepList[index]
            val isNotFirstItem = recipeStepList.size != 1
            RecipeItem(
                isNotFirstItem = isNotFirstItem,
                recipeStep = item,
                onAddRecipeDescription = { descriptionIndex ->
                    onAddRecipeDescription(index, descriptionIndex)
                }
            )
        }
        item {
            AddRecipeStep()
        }
    }
}

@Composable
private fun RecipeCookTime(
    cookingTimeHour: String,
    cookingTimeMin: String,
    onHourValueChange: (newValue: String) -> Unit,
    onMinValueChange: (newValue: String) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clock),
            contentDescription = stringResource(R.string.my_recipe_clock_icon_desc),
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
        Text(
            text = stringResource(R.string.my_recipe_total_cooking_time),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            HoneyNumberField(
                keyword = cookingTimeHour,
                hintText = stringResource(R.string.my_recipe_cooking_time_hour_hint),
                hintTextColor = OrderDetailRequirementHintColor,
                fontSize = 16.sp,
                isSingleLine = true,
                focusRequester = remember { FocusRequester() },
                onValueChange = { onHourValueChange(it) },
                onFocusChanged = {}
            )
        }
        Text(
            text = stringResource(R.string.my_recipe_cooking_time_hour),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            HoneyNumberField(
                keyword = cookingTimeMin,
                hintText = stringResource(R.string.my_recipe_cooking_time_minute_hint),
                hintTextColor = OrderDetailRequirementHintColor,
                fontSize = 16.sp,
                isSingleLine = true,
                focusRequester = remember { FocusRequester() },
                onValueChange = { onMinValueChange(it) },
                onFocusChanged = {}
            )
        }
        Text(
            text = stringResource(R.string.my_recipe_cooking_time_minute),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
private fun RecipeItem(
    isNotFirstItem: Boolean,
    recipeStep: RecipeStep,
    onAddRecipeDescription: (descriptionIndex: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 14.dp)
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.my_recipe_step, recipeStep.step),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (isNotFirstItem) {
                    IconButton(modifier = Modifier.size(32.dp), onClick = {}) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = stringResource(R.string.my_recipe_step_remove_icon_desc)
                        )
                    }
                }
            }
            var titleKeyword = ""
            Box(
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                HoneyTextField(
                    keyword = titleKeyword,
                    hintText = stringResource(R.string.my_recipe_step_title_hint),
                    hintTextColor = OrderDetailRequirementHintColor,
                    fontSize = 16.sp,
                    isSingleLine = true,
                    focusRequester = remember { FocusRequester() },
                    onValueChange = { titleKeyword = it },
                    onFocusChanged = {}
                )
            }
            for (index in recipeStep.description.indices) {
                Row(modifier = Modifier.padding(top = 5.dp), verticalAlignment = Alignment.CenterVertically) {
                    var contentKeyword = ""
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        HoneyTextField(
                            keyword = contentKeyword,
                            hintText = stringResource(R.string.my_recipe_step_description_hint),
                            hintTextColor = OrderDetailRequirementHintColor,
                            fontSize = 16.sp,
                            isSingleLine = false,
                            focusRequester = remember { FocusRequester() },
                            onValueChange = { contentKeyword = it },
                            onFocusChanged = {}
                        )
                    }
                    if (index == recipeStep.description.lastIndex) {
                        IconButton(modifier = Modifier.size(32.dp), onClick = { onAddRecipeDescription(index) }) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.my_recipe_step_description_add_icon_desc)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddRecipeStep() {
    RoundedBoxButton(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.White,
        borderColor = OrderDetailRequirementHintColor,
        rippleColor = Color.Gray,
        contentPadding = PaddingValues(vertical = 5.dp),
        onClick = {}
    ) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.my_recipe_add_step_icon_desc))
    }
}
