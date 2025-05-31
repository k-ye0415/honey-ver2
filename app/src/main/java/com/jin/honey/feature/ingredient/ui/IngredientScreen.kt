package com.jin.honey.feature.ingredient.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.ingredient.ui.content.IngredientHeader
import com.jin.honey.feature.ingredient.ui.content.IngredientTitle
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.PointColor

@Composable
fun IngredientScreen(
    viewModel: IngredientViewModel,
    menuName: String,
    onNavigateToCategory: () -> Unit
) {
    val menu by viewModel.menu.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchMenu(menuName)
    }

    when (val state = menu) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> IngredientSuccess(state.data, onNavigateToCategory)
        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun IngredientSuccess(menu: Menu, onNavigateToCategory: () -> Unit) {
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
    val statusTopHeightDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.toInt().dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navigationBarHeightDp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                IngredientHeader(
                    imageUrl = menu.imageUrl,
                    statusTopHeightDp = statusTopHeightDp,
                    onNavigateToCategory = onNavigateToCategory,
                    onClickShare = {},
                    onClickFavorite = {})
            }
            item {
                IngredientTitle(
                    menuName = menu.name,
                    onClickShowReview = {},
                    onClickShowRecipe = {},
                    onClickMyRecipe = {}
                )
            }
            item { IngredientBody(menu.name, menu.ingredient) }
        }
        IngredientAddedCart(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun IngredientBody(menuName: String, ingredientList: List<Ingredient>) {
    Text(
        text = stringResource(R.string.ingredient_all_add_cart),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
    val totalPrice = ingredientList.sumOf { it.unitPrice }
    val ingredient = Ingredient(menuName, "", totalPrice)
    IngredientItem(ingredient)
    HorizontalDivider(color = Color.LightGray)
    IngredientAccordion(ingredientList)
}

@Composable
fun IngredientAccordion(ingredientList: List<Ingredient>) {
    var isExpanded by remember { mutableStateOf(true) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.ingredient_show_ingredient),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = stringResource(R.string.ingredient_show_ingredient_icon_desc)
            )
        }

        AnimatedVisibility(visible = isExpanded) {
            Column {
                for (ingredient in ingredientList) {
                    IngredientItem(ingredient)
                }
            }
        }
    }
}

@Composable
private fun IngredientItem(ingredient: Ingredient) {
    Column {
        Row(
            modifier = Modifier.padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = false, onCheckedChange = {})
            Text(ingredient.name)
            Spacer(Modifier.width(4.dp))
            Text(ingredient.quantity)
            Spacer(Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { /* 수량 증가 */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                        modifier = Modifier.size(12.dp)
                    )
                }
                Text(
                    "1",
                    modifier = Modifier.width(20.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = { /* 수량 감소 */ }
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = stringResource(R.string.ingredient_remove_quantity_icon_desc),
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            Text(
                "${ingredient.unitPrice}원",
                modifier = Modifier
                    .width(80.dp)
                    .padding(end = 20.dp),
                textAlign = TextAlign.End
            )
        }
        HorizontalDivider()
    }
}

@Composable
fun IngredientAddedCart(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(1.dp, PointColor, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("** 외 2 가 장바구니에 있어요!", modifier = Modifier.padding(top = 14.dp))
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                onClick = {}
            ) {
                Text("***원 배달 주문하기")
            }
        }
    }
}
