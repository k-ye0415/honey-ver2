package com.jin.honey.feature.ingredient.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jin.honey.ui.theme.PointColor

@Composable
fun IngredientAddedCart(modifier: Modifier, onAddedCart: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .border(1.dp, PointColor, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("** 외 2 가 장바구니에 있어요!", modifier = Modifier.padding(top = 14.dp))
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                onClick = onAddedCart
            ) {
                Text("***원 배달 주문하기")
            }
        }
    }
}
