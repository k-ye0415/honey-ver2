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
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.HoneyTextField
import com.jin.RoundedBoxButton
import com.jin.domain.recipe.model.RecipeStep
import com.jin.ui.R
import com.jin.ui.theme.OrderDetailRequirementHintColor

@Composable
fun MyRecipeSteps(modifier: Modifier, recipeStepList: List<RecipeStep>) {
    LazyColumn(modifier = modifier) {
        item {
            RecipeCookTime()
        }
        items(recipeStepList.size) {
            val item = recipeStepList[it]
            RecipeItem(item)
        }
        item {
            AddRecipeStep()
        }
    }
}

@Composable
private fun RecipeCookTime() {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_clock),
            contentDescription = "",
            modifier = Modifier.size(20.dp),
            tint = Color.Unspecified
        )
        Text("총 조리 시간", modifier = Modifier.padding(horizontal = 4.dp))
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            var cookingTimeKeyword = ""
            HoneyTextField(
                keyword = cookingTimeKeyword,
                hintText = "0",
                hintTextColor = OrderDetailRequirementHintColor,
                fontSize = 16.sp,
                isSingleLine = true,
                focusRequester = remember { FocusRequester() },
                onValueChange = { cookingTimeKeyword = it },
                onFocusChanged = {}
            )
        }
        Text("시간", modifier = Modifier.padding(horizontal = 4.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            var cookingTimeKeyword = ""
            HoneyTextField(
                keyword = cookingTimeKeyword,
                hintText = "00",
                hintTextColor = OrderDetailRequirementHintColor,
                fontSize = 16.sp,
                isSingleLine = true,
                focusRequester = remember { FocusRequester() },
                onValueChange = { cookingTimeKeyword = it },
                onFocusChanged = {}
            )
        }
        Text("분", modifier = Modifier.padding(horizontal = 4.dp))
    }
}

@Composable
private fun RecipeItem(recipeStep: RecipeStep) {
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
                    text = "순서 ${recipeStep.step}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                IconButton(modifier = Modifier.size(32.dp), onClick = {}) {
                    Icon(Icons.Default.Remove, contentDescription = "")
                }
            }
            var titleKeyword = ""
            Box(
                modifier = Modifier
                    .padding(bottom = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                HoneyTextField(
                    keyword = titleKeyword,
                    hintText = "할일",
                    hintTextColor = OrderDetailRequirementHintColor,
                    fontSize = 16.sp,
                    isSingleLine = true,
                    focusRequester = remember { FocusRequester() },
                    onValueChange = { titleKeyword = it },
                    onFocusChanged = {}
                )
            }
            Row {
                var contentKeyword = ""
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    BasicTextField(
                        value = contentKeyword,
                        onValueChange = { contentKeyword = it },
                        singleLine = false,
                        modifier = Modifier
                            .focusRequester(remember { FocusRequester() })
                            .onFocusChanged { },
                        decorationBox = { innerTextField ->
                            if (contentKeyword.isEmpty()) {
                                Text(
                                    text = "할일2",
                                    color = OrderDetailRequirementHintColor,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
                IconButton(modifier = Modifier.size(32.dp), onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = "")
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
        Icon(Icons.Default.Add, contentDescription = "")
    }
}
