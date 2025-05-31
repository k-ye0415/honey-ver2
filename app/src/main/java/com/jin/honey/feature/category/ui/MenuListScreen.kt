package com.jin.honey.feature.category.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.CategoryType
import com.jin.honey.feature.food.domain.model.Food
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun MenuListScreen(menuList: List<Menu>, onNavigateToIngredient: (menuName: String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 14.dp),
    ) {
        items(menuList.size) { index ->
            val menu = menuList[index]
            MenuItem(menu, onNavigateToIngredient)
        }
    }
}

@Composable
private fun MenuItem(menu: Menu, onNavigateToIngredient: (menuName: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .padding(bottom = 10.dp)
            .clickable { onNavigateToIngredient(menu.name) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = menu.imageUrl,
            contentDescription = stringResource(R.string.menu_image_desc),
            modifier = Modifier
                .padding(end = 10.dp)
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = menu.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                SubButtonBox(stringResource(R.string.menu_recipe_button))
                Spacer(Modifier.width(8.dp))
                SubButtonBox(stringResource(R.string.menu_add_all_ingredient_button))
            }
        }
        IconButton({}) {
            Icon(Icons.Outlined.FavoriteBorder, contentDescription = stringResource(R.string.menu_favorite_icon_desc))
        }
    }
}

@Composable
private fun SubButtonBox(btnText: String) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = Color.Red,
                    bounded = true,
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { /* 클릭 처리 */ }
            )
            .border(1.dp, Color.Red, RoundedCornerShape(30.dp))
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 12.sp)
    }
}


@Composable
@Preview(showBackground = true)
fun BurgerScreenPreview() {
    HoneyTheme {
        val test = Food(
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
