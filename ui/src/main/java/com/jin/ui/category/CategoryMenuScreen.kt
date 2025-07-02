package com.jin.ui.category

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.BoxButton
import com.jin.domain.cart.model.Cart
import com.jin.domain.cart.model.IngredientCart
import com.jin.domain.food.model.CategoryType
import com.jin.domain.food.model.Menu
import com.jin.ui.R
import com.jin.ui.theme.PointColor
import java.time.Instant

@Composable
fun CategoryMenuScreen(
    menuList: List<Menu>,
    categoryType: CategoryType,
    favoriteList: List<String>,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onInsertCart: (cart: Cart) -> Unit,
    onClickFavorite: (menuName: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 14.dp),
    ) {
        items(menuList.size) { index ->
            val menu = menuList[index]
            val isFavorite = favoriteList.contains(menu.name)
            MenuItem(
                menu,
                categoryType,
                isFavorite,
                onNavigateToIngredient,
                onNavigateToRecipe,
                onInsertCart,
                onClickFavorite
            )
        }
    }
}

@Composable
private fun MenuItem(
    menu: Menu,
    categoryType: CategoryType,
    isFavorite: Boolean,
    onNavigateToIngredient: (menuName: String) -> Unit,
    onNavigateToRecipe: (menuName: String) -> Unit,
    onInsertCart: (cart: Cart) -> Unit,
    onClickFavorite: (menuName: String) -> Unit
) {
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
                BoxButton(
                    modifier = Modifier,
                    shape = RoundedCornerShape(30.dp),
                    backgroundColor = Color.White,
                    borderColor = PointColor,
                    rippleColor = PointColor,
                    contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
                    onClick = { onNavigateToRecipe(menu.name) }
                ) {
                    Text(
                        stringResource(R.string.menu_recipe_button),
                        fontSize = 12.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(Modifier.width(8.dp))
                BoxButton(
                    modifier = Modifier,
                    shape = RoundedCornerShape(30.dp),
                    backgroundColor = PointColor,
                    borderColor = PointColor,
                    rippleColor = Color.White,
                    contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
                    onClick = {
                        val ingredients = mutableListOf<IngredientCart>()
                        for (ingredient in menu.ingredient) {
                            val ingredientCart = IngredientCart(
                                name = ingredient.name,
                                cartQuantity = 1,
                                quantity = ingredient.quantity,
                                unitPrice = ingredient.unitPrice
                            )
                            ingredients.add(ingredientCart)
                        }
                        val cart = Cart(
                            id = null,
                            addedCartInstant = Instant.now(),
                            categoryType = categoryType,
                            menuName = menu.name,
                            menuImageUrl = menu.imageUrl,
                            ingredients = ingredients,
                            isOrdered = false
                        )
                        onInsertCart(cart)
                    }
                ) {
                    Text(
                        stringResource(R.string.menu_add_all_ingredient_button),
                        fontSize = 12.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        IconButton({ onClickFavorite(menu.name) }) {
            Icon(
                if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = stringResource(R.string.menu_favorite_icon_desc),
                tint = PointColor
            )
        }
    }
}
