package com.jin.ui.order.detail.content

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jin.ui.R
import com.jin.ui.theme.OrderDetailBoxBorderColor
import com.jin.ui.theme.OrderDetailDeleteIconColor
import com.jin.ui.theme.PointColor

@Composable
fun OrderDetailAgreeToTerms(
    isAllAgree: Boolean,
    termsMap: Map<String, Boolean>,
    modifier: Modifier,
    onAllAgreeChecked: (checked: Boolean) -> Unit,
    onAgreeChecked: (term: String, checked: Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, OrderDetailBoxBorderColor, RoundedCornerShape(8.dp))
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onAllAgreeChecked(!isAllAgree) }) {
                Checkbox(
                    checked = isAllAgree,
                    onCheckedChange = { onAllAgreeChecked(it) }
                )
                Text(text = stringResource(R.string.order_detail_terms_all), fontWeight = FontWeight.Bold)
            }
            HorizontalDivider()
            for ((termsTitle, isChecked) in termsMap) {
                Row(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable { onAgreeChecked(termsTitle, !isChecked) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = isChecked, onCheckedChange = { onAgreeChecked(termsTitle, it) })
                    Text(termsTitle, Modifier.weight(1f), fontSize = 14.sp)
                    Text(
                        text = stringResource(R.string.order_detail_terms_view_content),
                        fontSize = 12.sp,
                        textDecoration = TextDecoration.Underline,
                        color = OrderDetailDeleteIconColor
                    )
                }
            }
        }
    }
}

@Composable
fun NeedAgreeToTermsDialog(onShowDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { onShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 14.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.order_detail_dialog_terms_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                )

                Text(
                    text = stringResource(R.string.order_detail_dialog_terms_content),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onShowDialog(false) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PointColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.order_detail_dialog_terms_confirm),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}
