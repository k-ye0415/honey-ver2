package com.jin.honey.feature.orderdetail.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.cart.domain.model.Cart
import com.jin.honey.ui.theme.OrderDetailBoxBorderColor
import com.jin.honey.ui.theme.OrderDetailBoxDividerColor
import com.jin.honey.ui.theme.OrderDetailDeleteIconColor
import com.jin.honey.ui.theme.OrderDetailMenuClearTextColor

@Composable
fun OrderDetailCartItems(
    modifier: Modifier,
    cartItems: List<Cart>,
    onShowOptionBottomSheet: () -> Unit,
    onDeleteMenu: (cartItem: Cart) -> Unit,
    onDeleteIngredient: (cartItem: Cart, ingredientName: String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column {
            for (item in cartItems) {
                Column {
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)) {
                        Text(item.menuName, modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold)
                        Text(
                            stringResource(R.string.order_detail_cart_menu_clear),
                            color = OrderDetailMenuClearTextColor,
                            fontSize = 12.sp,
                            modifier = Modifier.clickable { onDeleteMenu(item) }
                        )
                    }
                    HorizontalDivider(color = OrderDetailBoxDividerColor)
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 14.dp)) {
                        AsyncImage(
                            model = item.menuImageUrl,
                            contentDescription = stringResource(R.string.order_detail_cart_menu_img_desc),
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .size(50.dp)
                                .background(Color.LightGray),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                            for (ingredient in item.ingredients) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(ingredient.name, modifier = Modifier.weight(2f))
                                    Text(ingredient.quantity, modifier = Modifier.weight(1f))
                                    Text(
                                        ingredient.cartQuantity.toString(),
                                        modifier = Modifier.width(30.dp),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = stringResource(R.string.order_detail_cart_ingredient_delete_icon_desc),
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clickable { onDeleteIngredient(item, ingredient.name) },
                                        tint = OrderDetailDeleteIconColor
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth()
                    .padding(bottom = 14.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.White)
                        .indication(
                            interactionSource = interactionSource,
                            indication = rememberRipple(
                                color = Color.Gray,
                                bounded = true,
                            )
                        )
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { onShowOptionBottomSheet() }
                        )
                        .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        stringResource(R.string.order_detail_cart_option_modify),
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
