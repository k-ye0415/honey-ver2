package com.jin.honey.feature.order.ui.content.orderhistory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.honey.R
import com.jin.honey.feature.payment.domain.model.Payment
import com.jin.honey.feature.payment.domain.model.PaymentState
import com.jin.honey.ui.theme.OrderHistoryBoxBorderColor
import com.jin.honey.ui.theme.OrderHistoryDateTimeTextColor
import com.jin.honey.ui.theme.OrderHistoryListBackgroundColor
import com.jin.honey.ui.theme.PointColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun OrderHistoryScreen(orderHistoryList: List<Payment>, onNavigateToPaymentDetail: (orderKey: String) -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_order_history),
                contentDescription = stringResource(R.string.order_history_icon_desc),
                tint = Color.Unspecified,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
            Text(stringResource(R.string.order_history_title), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        HorizontalDivider()
        if (orderHistoryList.isEmpty()) {
            // FIXME UI
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.background(OrderHistoryListBackgroundColor)
            ) {
                items(orderHistoryList.size) {
                    val item = orderHistoryList[it]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                    ) {
                        Row(modifier = Modifier.padding(bottom = 10.dp)) {
                            Text(
                                formatInstantToDataTime(item.payInstant),
                                color = OrderHistoryDateTimeTextColor,
                                fontSize = 12.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                PaymentState.findByStateLabel(item.payState.state),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                        Row {
                            AsyncImage(
                                model = item.cart.firstOrNull()?.menuImageUrl.orEmpty(),
                                contentDescription = stringResource(R.string.order_history_menu_img_desc),
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.height(80.dp)) {
                                Text(item.cart.firstOrNull()?.menuName.orEmpty(), fontWeight = FontWeight.Bold)
                                Text(
                                    item.cart.firstOrNull()?.ingredients?.firstOrNull()?.name.orEmpty(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(Modifier.weight(1f))
                                Row {
                                    CustomBoxButton(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        rippleColor = PointColor,
                                        borderColor = PointColor,
                                        btnText = stringResource(R.string.order_history_reorder),
                                        textColor = PointColor,
                                        fontWeight = FontWeight.Bold,
                                        onClickButton = {}
                                    )
                                    CustomBoxButton(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        rippleColor = Color.Gray,
                                        borderColor = OrderHistoryBoxBorderColor,
                                        btnText = stringResource(R.string.order_history_review),
                                        textColor = Color.Black,
                                        fontWeight = FontWeight.Normal,
                                        onClickButton = {}
                                    )
                                    CustomBoxButton(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        rippleColor = Color.Gray,
                                        borderColor = OrderHistoryBoxBorderColor,
                                        btnText = stringResource(R.string.order_history_order_detail),
                                        textColor = Color.Black,
                                        fontWeight = FontWeight.Normal,
                                        onClickButton = { onNavigateToPaymentDetail(item.orderKey) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CustomBoxButton(
    modifier: Modifier,
    rippleColor: Color,
    borderColor: Color,
    btnText: String,
    textColor: Color,
    fontWeight: FontWeight,
    onClickButton: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
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
            .border(1.dp, borderColor, RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(btnText, fontSize = 12.sp, color = textColor, fontWeight = fontWeight)
    }
}

private fun formatInstantToDataTime(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd a HH:mm", Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}
