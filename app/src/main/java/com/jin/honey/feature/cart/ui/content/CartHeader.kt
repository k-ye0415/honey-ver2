package com.jin.honey.feature.cart.ui.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun CartHeader() {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_shopping_basket),
            contentDescription = stringResource(R.string.order_cart_icon_desc),
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = 8.dp)
                .size(18.dp)
        )
        Text(stringResource(R.string.order_cart_title), fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
    HorizontalDivider()
}

@Composable
@Preview(showBackground = true)
fun CartHeaderPreview() {
    HoneyTheme {
        CartHeader()
    }
}
