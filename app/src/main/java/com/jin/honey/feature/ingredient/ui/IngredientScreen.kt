package com.jin.honey.feature.ingredient.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.AddRecipeBackgroundColor
import com.jin.honey.ui.theme.AddRecipeBorderColor
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun IngredientScreen(viewModel: IngredientViewModel, menuName: String) {
    val menu by viewModel.menu.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchMenu(menuName)
    }

    when (val state = menu) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> IngredientSuccess(state.data)
        is UiState.Error -> CircularProgressIndicator()
    }
}

@Composable
private fun IngredientSuccess(menu: Menu) {
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
    val statusTopHeightDp = WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.toInt().dp
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navigationBarHeightDp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { IngredientHeader(menu.imageUrl, statusTopHeightDp) }
            item { IngredientTitle(menu.name) }
            item { IngredientList(menu.name, menu.ingredient) }
        }
        IngredientAddedCart(Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun IngredientHeader(imageUrl: String, statusTopHeightDp: Dp) {
    Box {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.menu_image_desc),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .padding(top = statusTopHeightDp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp)
        ) {
            IconButton(
                modifier = Modifier.size(32.dp),
                colors = IconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black,
                ),
                onClick = {}) {
                Icon(
                    modifier = Modifier.scale(0.7f),
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.ingredient_back_icon_desc)
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                modifier = Modifier.size(32.dp),
                colors = IconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black,
                ),
                onClick = {}) {
                Icon(
                    modifier = Modifier.scale(0.7f),
                    imageVector = Icons.Default.Share,
                    contentDescription = stringResource(R.string.ingredient_share_icon_desc)
                )
            }
            Spacer(Modifier.width(8.dp))
            IconButton(
                modifier = Modifier.size(32.dp),
                colors = IconButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.White,
                    disabledContentColor = Color.Black,
                ),
                onClick = {}) {
                Icon(
                    modifier = Modifier.scale(0.7f),
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.menu_favorite_icon_desc)
                )
            }
        }
    }
}

@Composable
fun IngredientTitle(menuName: String) {
    val interactionSource = remember { MutableInteractionSource() }
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        text = menuName,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        textAlign = TextAlign.Center
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        color = Color.LightGray,
                        bounded = true,
                    )
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { /* 클릭 처리 */ }
                )
                .border(1.dp, Color.LightGray, RoundedCornerShape(30.dp))
                .padding(start = 8.dp, end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.ingredient_review_icon_desc),
                    tint = ReviewStarColor,
                )
                Text("리뷰 4.6(20)", fontSize = 12.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                Icon(
                    modifier = Modifier.size(14.dp),
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = stringResource(R.string.ingredient_review_move_icon_desc)
                )
            }
        }
        Spacer(Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color.White)
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        color = PointColor,
                        bounded = true,
                    )
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { /* 클릭 처리 */ }
                )
                .border(1.dp, PointColor, RoundedCornerShape(30.dp))
                .padding(start = 8.dp, end = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(R.string.menu_recipe_button),
                fontSize = 12.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
    Text(
        text = stringResource(R.string.ingredient_description),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        textAlign = TextAlign.Center,
        fontSize = 12.sp,
        color = Color.Gray
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(AddRecipeBackgroundColor)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = PointColor,
                    bounded = true,
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { /* 클릭 처리 */ }
            )
            .border(1.dp, AddRecipeBorderColor, RoundedCornerShape(8.dp))
            .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.ingredient_add_my_recipe),
            fontSize = 12.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun IngredientList(menuName: String, ingredientList: List<Ingredient>) {
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

@Composable
@Preview(showBackground = true)
private fun IngredientSuccessPreview() {
    HoneyTheme {
        IngredientSuccess(Menu(menuNames[0], "", ingredients[0]))
    }
}

val menuNames = listOf(
    "떡볶이",
    "김밥",
    "순대볶음",
    "튀김우동",
    "오뎅탕",
    "라볶이",
    "야끼만두",
    "찰순대",
    "참치마요 주먹밥",
    "고로케"
)

val ingredients = listOf(
    listOf(
        Ingredient(name = "떡", quantity = "150g", unitPrice = 1000),
        Ingredient(name = "고추장", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "고춧가루", quantity = "1작은술", unitPrice = 500),
        Ingredient(name = "설탕", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "어묵", quantity = "50g", unitPrice = 500),
        Ingredient(name = "대파", quantity = "20g", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "밥", quantity = "150g", unitPrice = 500),
        Ingredient(name = "김", quantity = "1장", unitPrice = 500),
        Ingredient(name = "단무지", quantity = "20g", unitPrice = 500),
        Ingredient(name = "우엉조림", quantity = "20g", unitPrice = 500),
        Ingredient(name = "맛살", quantity = "1줄", unitPrice = 500),
        Ingredient(name = "계란", quantity = "1개", unitPrice = 500),
        Ingredient(name = "시금치", quantity = "30g", unitPrice = 500),
        Ingredient(name = "참기름", quantity = "1작은술", unitPrice = 500),
        Ingredient(name = "소금", quantity = "약간", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "순대", quantity = "150g", unitPrice = 1000),
        Ingredient(name = "양배추", quantity = "50g", unitPrice = 500),
        Ingredient(name = "대파", quantity = "20g", unitPrice = 500),
        Ingredient(name = "고추장", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "고춧가루", quantity = "1작은술", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "우동면", quantity = "1인분", unitPrice = 1000),
        Ingredient(name = "유부", quantity = "2조각", unitPrice = 500),
        Ingredient(name = "어묵", quantity = "30g", unitPrice = 500),
        Ingredient(name = "국물베이스", quantity = "200ml", unitPrice = 500),
        Ingredient(name = "튀김", quantity = "1개", unitPrice = 500),
        Ingredient(name = "파", quantity = "약간", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "어묵", quantity = "100g", unitPrice = 1000),
        Ingredient(name = "무", quantity = "50g", unitPrice = 500),
        Ingredient(name = "대파", quantity = "20g", unitPrice = 500),
        Ingredient(name = "국간장", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "육수", quantity = "300ml", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "떡", quantity = "100g", unitPrice = 500),
        Ingredient(name = "라면사리", quantity = "1/2개", unitPrice = 500),
        Ingredient(name = "고추장", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "설탕", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "어묵", quantity = "50g", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "만두", quantity = "5개", unitPrice = 1000),
        Ingredient(name = "간장", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "식초", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "고춧가루", quantity = "1작은술", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "순대", quantity = "150g", unitPrice = 1000),
        Ingredient(name = "소금", quantity = "약간", unitPrice = 500),
        Ingredient(name = "쌈장", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "깻잎", quantity = "2장", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "밥", quantity = "150g", unitPrice = 500),
        Ingredient(name = "참치캔", quantity = "1/2개", unitPrice = 1000),
        Ingredient(name = "마요네즈", quantity = "1큰술", unitPrice = 500),
        Ingredient(name = "김", quantity = "1장", unitPrice = 500),
        Ingredient(name = "소금", quantity = "약간", unitPrice = 500)
    ),
    listOf(
        Ingredient(name = "감자", quantity = "1개", unitPrice = 500),
        Ingredient(name = "양파", quantity = "30g", unitPrice = 500),
        Ingredient(name = "햄", quantity = "30g", unitPrice = 500),
        Ingredient(name = "밀가루", quantity = "2큰술", unitPrice = 500),
        Ingredient(name = "달걀", quantity = "1개", unitPrice = 500),
        Ingredient(name = "빵가루", quantity = "3큰술", unitPrice = 500)
    )
)
