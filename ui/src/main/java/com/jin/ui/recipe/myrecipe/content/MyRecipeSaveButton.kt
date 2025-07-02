package com.jin.ui.recipe.myrecipe.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.RoundedBoxButton
import com.jin.ui.theme.PointColor

@Composable
fun MyRecipeSaveButton(){
    RoundedBoxButton(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        backgroundColor = PointColor,
        borderColor = PointColor,
        rippleColor = Color.White,
        contentPadding = PaddingValues(vertical = 5.dp),
        onClick = {}
    ) {
        Text("저장하기", color = Color.White, fontWeight = FontWeight.Bold)
    }
}
