package com.jin.honey.feature.reviewwrite.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.honey.R
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.food.domain.model.Menu
import com.jin.honey.feature.food.domain.model.Recipe
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor
import com.jin.honey.ui.theme.ReviewStarColor

@Composable
fun ReviewWriteScreen(paymentId: Int) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton({}) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.review_back_icon_desc),
                        tint = Color.Black
                    )
                }
                Text(
                    text = "menuName",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                "이 메뉴를 추천하시겠어요?",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 14.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                "${menuFallback.name} 외 1 [${menuFallback.ingredient.firstOrNull()?.name} 외 1]",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(top = 4.dp),
                fontSize = 14.sp
            )
            SelectableRatingBar(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 8.dp),
                initialRating = 0.0,
                starSize = 48.dp,
                onRatingChanged = {})
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.review_score_taste_quantity))
                SelectableRatingBar(modifier = Modifier, initialRating = 0.0, starSize = 32.dp, onRatingChanged = {})
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.review_score_recipe))
                SelectableRatingBar(modifier = Modifier, initialRating = 0.0, starSize = 32.dp, onRatingChanged = {})
            }
            var text by remember { mutableStateOf("") }
            val maxLength = 1000
            val minLines = 5
            val maxLines = 10
            OutlinedTextField(
                value = text,
                onValueChange = { newValue ->
                    // 최대 글자 수를 넘지 않도록 제한
                    if (newValue.length <= maxLength) {
                        text = newValue
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth()
                    .heightIn(min = (20 * minLines).dp, max = (20 * maxLines).dp), // 폰트 크기에 따라 적절한 dp 조절 필요
                placeholder = { // 플레이스홀더 텍스트
                    Text(
                        text = "최소 10자 이상 작성해야 등록이 가능해요",
                        color = Color.Gray,
                        fontSize = 14.sp // 플레이스홀더 폰트 크기 조정
                    )
                },
                textStyle = TextStyle(fontSize = 16.sp), // 입력 텍스트 폰트 크기 조정
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color.LightGray, // 포커스 시 테두리 색상 (이미지와 유사하게)
//                    unfocusedBorderColor = Color.LightGray, // 비포커스 시 테두리 색상 (이미지와 유사하게)
//                    cursorColor = Color.Black // 커서 색상
//                ),
                // 이 부분을 통해 여러 줄 입력 지원
                singleLine = false,
                maxLines = maxLines, // 스크롤이 발생하기 전까지의 최대 라인 수
            )
            Text(
                text = "0/1000",
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                onClick = {}
            ) {
                Text(text = stringResource(R.string.onboarding_button_confirm), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SelectableRatingBar(
    modifier: Modifier,
    initialRating: Double,
    starSize: Dp = 32.dp, // 이미지에 맞춰 별 크기 조정
    onRatingChanged: (Double) -> Unit // 별점이 변경될 때 호출될 콜백
) {
    // 현재 선택된 별점을 저장하는 상태 변수
    var currentRating by remember { mutableDoubleStateOf(initialRating) }
    val maxStars = 5

    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        for (i in 1..maxStars) {
            val tintColor = if (currentRating >= i) ReviewStarColor else Color(0xFFD3D3D3)

            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "$i star rating", // 접근성 설명 추가
                tint = tintColor,
                modifier = Modifier
                    .size(starSize)
                    .clickable {
                        // 클릭된 별의 인덱스를 새로운 별점으로 설정
                        currentRating = i.toDouble()
                        onRatingChanged(currentRating) // 변경된 별점 값 콜백으로 전달
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewWriteScreenPreview() {
    HoneyTheme {
        ReviewWriteScreen(1)
    }
}

val menuFallback = Menu(
    name = "Mable McIntosh",
    imageUrl = "https://duckduckgo.com/?q=reque",
    recipe = Recipe(cookingTime = "vehicula", recipeSteps = listOf()),
    ingredient = listOf(Ingredient(name = "Elnora Peters", quantity = "ornare", unitPrice = 9320))

)
