package com.jin.honey.feature.review.ui.content

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewShowOption(){
    Row(modifier = Modifier.padding(bottom = 10.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = false, onCheckedChange = {})
        Text("사진리뷰 보기", fontSize = 14.sp)
        Spacer(Modifier.weight(1f))
        Row(modifier = Modifier.padding(end = 10.dp)) {
            Text("최신순", fontSize = 14.sp)
            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "")
        }
    }
}
