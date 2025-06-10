package com.jin.honey.feature.review.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jin.honey.feature.food.domain.model.Ingredient
import com.jin.honey.feature.review.ui.content.ReviewHeader
import com.jin.honey.feature.review.ui.content.ReviewItem
import com.jin.honey.feature.review.ui.content.ReviewScore
import com.jin.honey.feature.review.ui.content.ReviewShowOption
import com.jin.honey.ui.theme.HoneyTheme

@Composable
fun ReviewScreen(menuName: String) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ReviewHeader(menuName)
            LazyColumn() {
                item {
                    ReviewScore()
                }
                item {
                    HorizontalDivider(thickness = 4.dp)
                }
                item {
                    ReviewShowOption()
                }
                items(fallbackData.size) {
                    val item = fallbackData[it]
                    ReviewItem(item)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ReviewScreen12() {
    HoneyTheme {
        ReviewScreen("치즈버거")
    }
}

val fallbackData = listOf(
    "겉은 부드럽고 속은 촉촉한 치즈 오믈렛! 치즈가 쭉 늘어나면서 고소함이 가득해서 정말 맛있었어요.",
    "계란이 정말 부드러워서 입안에서 사르르 녹는 느낌이에요. 집에서 만든 것보다 훨씬 맛있습니다.",
    "치즈가 듬뿍 들어가서 고소함이 배가 됩니다. 아침 식사 대용으로 최고예요.",
    "간이 딱 맞아서 다른 소스 없이도 맛있게 즐길 수 있었습니다. 기본에 충실한 맛!",
    "따뜻할 때 먹으니 더욱 고소하고 부드러웠어요. 갓 만든 오믈렛이 최고죠!",
    "생각보다 양이 많아서 든든하게 먹었어요. 가성비도 훌륭합니다.",
    "치즈 오믈렛 맛집 찾고 있었는데, 드디어 인생 오믈렛을 찾았습니다! 재방문 의사 100%!",
    "아이들이 특히 좋아하는 메뉴예요. 부드러워서 안심하고 먹일 수 있습니다.",
    "깔끔하고 담백해서 질리지 않고 계속 먹을 수 있습니다. 속이 편안해요.",
    "케첩이나 핫소스 살짝 뿌려 먹으니 또 다른 매력이 있네요.",
    "빵이랑 같이 먹으니 더욱 든든하고 맛있었습니다. 브런치 메뉴로 최고!",
    "계란 비린 맛 전혀 없이 깔끔하고 고소합니다.",
    "부드러운 식감 덕분에 계속 손이 갑니다. 중독성 있어요.",
    "간단하지만 훌륭한 맛을 내는 오믈렛입니다. 추천해요!",
    "치즈가 아낌없이 들어가 있어서 정말 만족스러웠습니다.",
    "새우가 정말 통통하고 신선해서 만족스러웠어요. 버터 소스에 푹 담겨 더욱 맛있습니다.",
    "마늘이 듬뿍 들어가서 향긋하고 감칠맛이 폭발합니다. 느끼함 없이 깔끔해요.",
    "바게트 빵에 소스 듬뿍 찍어 먹으니 정말 꿀맛! 빵 추가는 필수입니다.",
    "술안주로 시켰는데, 술이 술술 넘어가는 맛이었어요. 와인과 찰떡궁합입니다.",
    "생각보다 양이 많아서 든든하게 먹었어요. 여럿이서 나눠 먹기 좋습니다.",
    "갈릭버터 쉬림프 맛집 찾고 있었는데, 드디어 인생 쉬림프를 찾았습니다! 재방문 의사 100%!",
    "따뜻하게 먹으니 더욱 맛있었어요. 버터의 풍미가 살아있습니다.",
    "깔끔하고 담백해서 질리지 않고 계속 먹을 수 있습니다. 속이 편안해요.",
    "다른 해산물 요리랑 같이 시켜도 잘 어울리는 메뉴예요. 밸런스가 좋습니다.",
    "새우가 정말 탱글탱글해서 식감이 아주 좋았습니다. 신선함이 느껴져요.",
    "이국적인 향신료 맛이 입맛을 돋우고, 먹을수록 매력에 빠집니다.",
    "집에서 해 먹고 싶을 정도로 맛있었습니다. 레시피가 궁금하네요.",
    "마늘 후레이크가 바삭해서 식감도 좋고 더욱 고소했습니다.",
    "올리브 오일과 버터의 조합이 완벽해서 소스까지 다 먹었어요."
)

val ingredientFallback = listOf(
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
    Ingredient(name = "Jody Mann", quantity = "ridiculus", unitPrice = 3132),
)
