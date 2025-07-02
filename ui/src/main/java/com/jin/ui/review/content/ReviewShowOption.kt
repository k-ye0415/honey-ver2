package com.jin.ui.review.content

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.ui.R

@Composable
fun ReviewShowOption() {
    Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = false, onCheckedChange = {})
        Text(text = stringResource(R.string.review_show_photo), fontSize = 14.sp)
        Spacer(Modifier.weight(1f))
        Row(modifier = Modifier.padding(end = 10.dp)) {
            Text(text = stringResource(R.string.review_latest_order), fontSize = 14.sp)
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.review_order_icon_desc))
        }
    }
}
