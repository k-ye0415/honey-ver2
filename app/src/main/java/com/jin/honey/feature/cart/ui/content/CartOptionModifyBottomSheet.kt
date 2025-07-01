package com.jin.honey.feature.cart.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.feature.cart.domain.model.CartKey
import com.jin.honey.feature.cart.domain.model.IngredientCart
import com.jin.ui.theme.PointColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartOptionModifyBottomSheet(
    cartItems: List<Cart>,
    onRemoveCart: (cartItem: Cart, ingredientName: String) -> Unit,
    onBottomSheetClose: (state: Boolean) -> Unit,
    onChangeOption: (quantityMap: Map<CartKey, Int>) -> Unit,
) {
    val quantityMap = remember {
        mutableStateMapOf<CartKey, Int>().apply {
            for (cart in cartItems) {
                for (ingredient in cart.ingredients) {
                    put(CartKey(cart.menuName, ingredient.name), ingredient.cartQuantity)
                }
            }
        }
    }
    ModalBottomSheet(
        onDismissRequest = { onBottomSheetClose(false) },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Box(modifier = Modifier.fillMaxHeight()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                BottomSheetHeader(onBottomSheetClose)
                LazyColumn(contentPadding = PaddingValues(bottom = 4.dp)) {
                    items(cartItems.size) {
                        val cartItem = cartItems[it]
                        IngredientItems(
                            cartItem.menuName,
                            cartItem.ingredients,
                            quantityMap,
                            onQuantityChange = { ingredientName, newQuantity ->
                                quantityMap[CartKey(
                                    cartItem.menuName,
                                    ingredientName
                                )] = newQuantity
                            },
                            onRemoveCart = { ingredientName ->
                                quantityMap.remove(CartKey(cartItem.menuName, ingredientName))
                                onRemoveCart(cartItem, ingredientName)
                            }
                        )
                    }
                }
            }
            BottomSheetButtons(
                modifier = Modifier.align(Alignment.BottomCenter),
                onBottomSheetClose = onBottomSheetClose,
                onChangeOption = { onChangeOption(quantityMap) }
            )
        }
    }
}

@Composable
private fun BottomSheetHeader(onBottomSheetClose: (state: Boolean) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = stringResource(R.string.cart_modify_option),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            modifier = Modifier.size(32.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.White,
                contentColor = Color.Black,
            ),
            onClick = { onBottomSheetClose(false) }
        ) {
            Icon(
                modifier = Modifier.scale(0.7f),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.cart_modify_option_close_icon_desc)
            )
        }
    }
}

@Composable
private fun IngredientItems(
    menuName: String,
    ingredients: List<IngredientCart>,
    quantityMap: Map<CartKey, Int>,
    onQuantityChange: (ingredientName: String, newQuantity: Int) -> Unit,
    onRemoveCart: (ingredientName: String) -> Unit,
) {
    Column {
        Text(
            menuName,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
        HorizontalDivider()
        for (ingredient in ingredients) {
            var cartQuantity = quantityMap[CartKey(menuName, ingredient.name)] ?: 1

            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .padding(start = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("${ingredient.name} ${ingredient.quantity}", fontSize = 18.sp)
                    Spacer(Modifier.weight(1f))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                modifier = Modifier
                                    .padding(end = 4.dp)
                                    .size(32.dp),
                                onClick = {
                                    if (cartQuantity < 10) {
                                        cartQuantity++
                                        onQuantityChange(ingredient.name, cartQuantity)
                                    }
                                },
                                enabled = cartQuantity < 10
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                                    modifier = Modifier.scale(0.7f),
                                    tint = if (cartQuantity < 10) Color.Black else Color.LightGray
                                )
                            }
                            Text(
                                text = "$cartQuantity",
                                modifier = Modifier.width(20.dp),
                                textAlign = TextAlign.Center
                            )
                            IconButton(
                                modifier = Modifier
                                    .padding(start = 4.dp)
                                    .size(32.dp),
                                onClick = {
                                    if (cartQuantity > 1) {
                                        cartQuantity--
                                        onQuantityChange(ingredient.name, cartQuantity)
                                    }
                                },
                                enabled = cartQuantity > 1
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Remove,
                                    contentDescription = stringResource(R.string.ingredient_remove_quantity_icon_desc),
                                    modifier = Modifier.scale(0.7f),
                                    tint = if (cartQuantity == 1) Color.LightGray else Color.Black
                                )
                            }
                        }
                    }
                    Spacer(Modifier.width(10.dp))
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black,
                        ),
                        onClick = { onRemoveCart(ingredient.name) }
                    ) {
                        Icon(
                            modifier = Modifier.scale(0.7f),
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.cart_modify_option_close_icon_desc)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomSheetButtons(
    modifier: Modifier,
    onBottomSheetClose: (state: Boolean) -> Unit,
    onChangeOption: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomButton(
            modifier = Modifier.weight(1f),
            backgroundColor = Color.White,
            borderColor = PointColor,
            rippleColor = PointColor,
            btnText = stringResource(R.string.cart_modify_option_cancel),
            btnTextColor = Color.Black,
            onClickEvent = { onBottomSheetClose(false) }
        )
        Spacer(Modifier.width(10.dp))
        CustomButton(
            modifier = Modifier.weight(1f),
            backgroundColor = PointColor,
            borderColor = PointColor,
            rippleColor = Color.White,
            btnText = stringResource(R.string.cart_modify_option_modify),
            btnTextColor = Color.White,
            onClickEvent = onChangeOption
        )
    }
}

@Composable
private fun CustomButton(
    modifier: Modifier,
    backgroundColor: Color,
    borderColor: Color,
    rippleColor: Color,
    btnText: String,
    btnTextColor: Color,
    onClickEvent: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .indication(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    color = rippleColor,
                    bounded = true,
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickEvent
            )
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, color = btnTextColor)
    }
}
