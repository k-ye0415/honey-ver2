package com.jin.ui.order.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jin.BoxButton
import com.jin.domain.order.model.Order
import com.jin.domain.order.model.PaymentState
import com.jin.ui.R
import com.jin.ui.theme.OrderHistoryBoxBorderColor
import com.jin.ui.theme.OrderHistoryDateTimeTextColor
import com.jin.ui.theme.OrderHistoryListBackgroundColor
import com.jin.ui.theme.PointColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun OrderHistoryScreen(
    orderHistoryList: List<Order>,
    onNavigateToWriteReview: (orderKey: String) -> Unit,
    onNavigateToPaymentDetail: (orderKey: String) -> Unit
) {
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
                                    BoxButton(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        shape = RoundedCornerShape(4.dp),
                                        backgroundColor = Color.White,
                                        borderColor = PointColor,
                                        rippleColor = PointColor,
                                        contentPadding = PaddingValues(),
                                        onClick = {}) {
                                        Text(
                                            stringResource(R.string.order_history_reorder),
                                            fontSize = 12.sp,
                                            color = PointColor,
                                            fontWeight = FontWeight.Bold,
                                        )
                                    }
                                    BoxButton(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        shape = RoundedCornerShape(4.dp),
                                        backgroundColor = Color.White,
                                        borderColor = OrderHistoryBoxBorderColor,
                                        rippleColor = Color.Gray,
                                        contentPadding = PaddingValues(),
                                        onClick = { onNavigateToWriteReview(item.orderKey) }
                                    ) {
                                        Text(
                                            stringResource(R.string.order_history_review),
                                            fontSize = 12.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Normal,
                                        )
                                    }
                                    BoxButton(
                                        modifier = Modifier
                                            .padding(end = 4.dp)
                                            .weight(1f),
                                        shape = RoundedCornerShape(4.dp),
                                        backgroundColor = Color.White,
                                        borderColor = OrderHistoryBoxBorderColor,
                                        rippleColor = Color.Gray,
                                        contentPadding = PaddingValues(),
                                        onClick = { onNavigateToPaymentDetail(item.orderKey) }
                                    ) {
                                        Text(
                                            stringResource(R.string.order_history_order_detail),
                                            fontSize = 12.sp,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Normal,
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
}

private fun formatInstantToDataTime(instant: Instant): String {
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd a HH:mm", Locale.getDefault())
        .withZone(ZoneId.systemDefault())

    return formatter.format(instant)
}
