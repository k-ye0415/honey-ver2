package com.jin.honey.feature.reviewwrite.ui.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.reviewwrite.ui.SelectableRatingBar
import com.jin.honey.ui.theme.PointColor

@Composable
fun MenuReviewWriteScreen(menu: Menu, btnText: String) {
    var text by remember { mutableStateOf("") }
    val maxLength = 1000
    val minLines = 5
    val maxLines = 10

    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(top = 14.dp)
    ) {
        Text(menu.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("${menu.ingredient.firstOrNull()?.name} 외 1")
        SelectableRatingBar(
            modifier = Modifier.padding(bottom = 8.dp),
            initialRating = 0.0,
            starSize = 48.dp,
            onRatingChanged = {})
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.review_score_taste_quantity))
            SelectableRatingBar(modifier = Modifier, initialRating = 0.0, starSize = 32.dp, onRatingChanged = {})
        }
        Row(
            modifier = Modifier.padding(bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(R.string.review_score_recipe))
            SelectableRatingBar(modifier = Modifier, initialRating = 0.0, starSize = 32.dp, onRatingChanged = {})
        }

        OutlinedTextField(
            value = text,
            onValueChange = { newValue ->
                // 최대 글자 수를 넘지 않도록 제한
                if (newValue.length <= maxLength) {
                    text = newValue
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = (20 * minLines).dp, max = (20 * maxLines).dp), // 폰트 크기에 따라 적절한 dp 조절 필요
            placeholder = { // 플레이스홀더 텍스트
                Text(
                    text = "최소 10자 이상 작성해야 등록이 가능해요",
                    color = Color.Gray,
                    fontSize = 14.sp // 플레이스홀더 폰트 크기 조정
                )
            },
            textStyle = TextStyle(fontSize = 16.sp),
            singleLine = false,
            maxLines = maxLines,
        )
        Text(
            text = "0/1000",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.End
        )
        Button(
            enabled = text.isNotEmpty(),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
            onClick = {}
        ) {
            Text(text = btnText, fontWeight = FontWeight.Bold)
        }
    }

}
