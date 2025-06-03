package com.jin.honey.feature.order.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor

@Composable
fun OrderScreen(viewModel: OrderViewModel) {
    val fallbackData = listOf("주문내역", "주문내역", "주문내역", "주문내역", "주문내역", "주문내역")
    Column() {
        // title
        Text(
            text = "주문",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        // cart
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.ShoppingCart, contentDescription = "")
            Text(
                "장바구니", fontSize = 18.sp
            )
        }
        HorizontalDivider()
        // cart content
        Box(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(modifier = Modifier.height(100.dp)) {
                AsyncImage(
                    model = "",
                    contentDescription = "",
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(100.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("메뉴 이름 외 1", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Text("재료들이 쭈루룩~", fontSize = 14.sp)
                    Spacer(Modifier.weight(1f))
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                        SubButtonBox(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 2.dp),
                            btnText = "옵션 수정",
                            backgroundColor = Color.White,
                            rippleColor = PointColor,
                            textColor = Color.Black
                        )
                        SubButtonBox(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 2.dp),
                            btnText = "주문하기",
                            backgroundColor = PointColor,
                            rippleColor = PointColor,
                            textColor = Color.White
                        )
                    }
                }
            }
        }

        // order
        Text("주문내역")
        HorizontalDivider()
        // FIXME : 주문내역 정의 후 UI 수정
        LazyColumn {
            items(fallbackData.size) {
                Text(fallbackData[it])
            }
        }
    }

}

@Composable
private fun SubButtonBox(
    modifier: Modifier,
    btnText: String,
    backgroundColor: Color,
    rippleColor: Color,
    textColor: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
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
                onClick = { /* 클릭 처리 */ }
            )
            .border(1.dp, PointColor, RoundedCornerShape(30.dp))
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 12.sp, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
@Preview(showBackground = true)
fun OrderScreenPreview() {
    HoneyTheme {
        OrderScreen(OrderViewModel())
    }
}
