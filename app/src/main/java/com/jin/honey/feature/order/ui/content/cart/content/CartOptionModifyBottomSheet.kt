package com.jin.honey.feature.order.ui.content.cart.content

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.order.ui.cartFallback
import com.jin.honey.ui.theme.PointColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartOptionModifyBottomSheet(onBottomSheetClose: (state: Boolean) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = { onBottomSheetClose(false) },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.cart_modify_option),
                    fontSize = 18.sp,
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
            LazyColumn(contentPadding = PaddingValues(bottom = 4.dp)) {
                items(cartFallback.size) {
                    val menu = cartFallback[it]
                    Column {
                        Text(
                            menu.name,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                        HorizontalDivider()
                        Row(
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .padding(start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val ingredientList = menu.ingredient
                            for (ingredient in ingredientList) {
                                Text(ingredient.name)
                                Spacer(Modifier.weight(1f))
                                Row {
                                    IconButton(
                                        modifier = Modifier.size(32.dp),
                                        onClick = {}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = stringResource(R.string.ingredient_plus_quantity_icon_desc),
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                    Text(
                                        "${ingredient.quantity}",
                                        modifier = Modifier.width(20.dp),
                                        textAlign = TextAlign.Center
                                    )
                                    IconButton(
                                        modifier = Modifier.size(32.dp),
                                        onClick = {}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Remove,
                                            contentDescription = stringResource(R.string.ingredient_remove_quantity_icon_desc),
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Row {
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                    onClick = {}
                ) {
                    Text(stringResource(R.string.cart_modify_option_cancel))
                }
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                    onClick = {}
                ) {
                    Text(stringResource(R.string.cart_modify_option_modify))
                }
            }
        }
    }
}
