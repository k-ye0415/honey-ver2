package com.jin.honey.feature.ingredient.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.ui.state.UiState
import com.jin.honey.ui.theme.HoneyTheme

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
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = navigationBarHeightDp)) {
        Column(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = menu.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Text(menu.name)
            Row {
                Button({}) { Text("리뷰") }
                Button({}) { Text("레시피 보기") }
            }
            LazyColumn {
                items(menu.ingredient.size) {
                    val ingredient = menu.ingredient[it]
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = false, {})
                        Text(ingredient.name)
                        Text(ingredient.quantity)
                        Spacer(Modifier.weight(1f))
                        IconButton(modifier = Modifier
                            .size(32.dp), onClick = {}) {
                            Icon(Icons.Default.Add, contentDescription = "", modifier = Modifier.size(12.dp))
                        }
                        Text("1")
                        IconButton(modifier = Modifier
                            .size(32.dp), onClick = {}) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = "", modifier = Modifier.size(12.dp)
                            )
                        }
                        Text("${ingredient.unitPrice}원", modifier = Modifier.padding(end = 20.dp))
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(Color.LightGray),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("** 외 2")
                Button({}) {
                    Text("***원 배달 주문하기")
                }
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
