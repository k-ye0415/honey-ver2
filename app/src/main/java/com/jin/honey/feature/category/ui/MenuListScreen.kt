package com.jin.honey.feature.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jin.honey.feature.food.domain.model.Category
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun MenuListScreen(menuList: List<Menu>, onNavigateToIngredient: (menuName: String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 4.dp),
    ) {
        items(menuList.size) { index ->
            val menu = menuList[index]
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .clickable { onNavigateToIngredient(menu.name) }
            ) {
                AsyncImage(
                    model = menu.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(100.dp)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(menu.name)
                    Button({}) {
                        Text("모든 재료 담기")
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun BurgerScreenPreview() {
    HoneyTheme {
        val test = Category(
            categoryType = CategoryType.Burger,
            menu = listOf(
                Menu(
                    name = "치즈버거",
                    imageUrl = "",
                    ingredient = listOf(Ingredient(name = "햄버거 번", quantity = "1개", unitPrice = 1000))
                ),
                Menu(
                    name = "치즈버거dasgae",
                    imageUrl = "",
                    ingredient = listOf(Ingredient(name = "햄버거 번", quantity = "1개", unitPrice = 1000))
                ),
                Menu(
                    name = "치즈버거agewgf2erf121212433",
                    imageUrl = "",
                    ingredient = listOf(Ingredient(name = "햄버거 번", quantity = "1개", unitPrice = 1000))
                ),
            )
        )
        MenuListScreen(test.menu) {}
    }
}
