package com.jin.honey.feature.paymentdetail.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.ui.theme.PayDetailBoxBorderColor
import com.jin.honey.ui.theme.PayDetailDividerColor
import com.jin.honey.ui.theme.PointColor

@Composable
fun PayDetailOverView(menuName: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 16.dp, bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = menuName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Icon(
            Icons.Default.ArrowForwardIos,
            contentDescription = stringResource(R.string.payment_detail_enter_menu_icon_desc),
            modifier = Modifier.size(18.dp)
        )
    }
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomBoxButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            rippleColor = PointColor,
            borderColor = PointColor,
            btnText = stringResource(R.string.order_history_reorder),
            textColor = PointColor,
            onClickButton = {}
        )
        CustomBoxButton(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp),
            rippleColor = Color.Gray,
            borderColor = PayDetailBoxBorderColor,
            btnText = stringResource(R.string.order_history_review),
            textColor = Color.Black,
            onClickButton = {}
        )
    }
    HorizontalDivider(color = PayDetailDividerColor)
}


@Composable
private fun CustomBoxButton(
    modifier: Modifier,
    rippleColor: Color,
    borderColor: Color,
    btnText: String,
    textColor: Color,
    onClickButton: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .indication(
                interactionSource,
                rememberRipple(color = rippleColor, bounded = true)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickButton
            )
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 14.sp, color = textColor)
    }
}
