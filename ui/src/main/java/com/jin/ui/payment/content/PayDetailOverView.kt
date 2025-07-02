package com.jin.ui.payment.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.BoxButton
import com.jin.ui.R
import com.jin.ui.theme.PayDetailBoxBorderColor
import com.jin.ui.theme.PayDetailDividerColor
import com.jin.ui.theme.PointColor

@Composable
fun PayDetailOverView(menuName: String) {
    Text(
        text = menuName,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 16.dp, bottom = 14.dp)
    )
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // FIXME reorder
        BoxButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            borderColor = PointColor,
            rippleColor = PointColor,
            contentPadding = PaddingValues(vertical = 8.dp),
            onClick = {},
        ) {
            Text(text = stringResource(R.string.order_history_reorder), fontSize = 14.sp, color = PointColor)
        }
        // FIXME review write
        BoxButton(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = Color.White,
            borderColor = PayDetailBoxBorderColor,
            rippleColor = Color.Gray,
            contentPadding = PaddingValues(vertical = 8.dp),
            onClick = {},
        ) {
            Text(text = stringResource(R.string.order_history_review), fontSize = 14.sp, color =  Color.Black,)
        }
    }
    HorizontalDivider(color = PayDetailDividerColor)
}
