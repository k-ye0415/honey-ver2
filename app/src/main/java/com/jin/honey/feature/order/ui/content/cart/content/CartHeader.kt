package com.jin.honey.feature.order.ui.content.cart.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R

@Composable
fun CartHeader(){
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Filled.ShoppingCart, contentDescription = stringResource(R.string.order_cart_icon_desc))
        Text(stringResource(R.string.order_cart_title), fontSize = 18.sp)
    }
    HorizontalDivider()
}
