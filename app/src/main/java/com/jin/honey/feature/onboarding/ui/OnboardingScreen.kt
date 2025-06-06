package com.jin.honey.feature.onboarding.ui

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.jin.honey.R
import com.jin.honey.ui.theme.HoneyTheme
import com.jin.honey.ui.theme.PointColor

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, onNavigateToMain: () -> Unit) {
    val composePermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    )

    LaunchedEffect(composePermissionState.status) {
        if (composePermissionState.status is PermissionStatus.Granted) {
            viewModel.onLocationPermissionGranted()
            onNavigateToMain()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.syncAllMenu()
    }
    WelcomeScreen { composePermissionState.launchPermissionRequest() }

}

@Composable
fun WelcomeScreen(onPermissionRequest: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xfff3f4f5))
            ) {
                Text(
                    "꿀재료 이용을 위해 \n앱 접근 권한이 필요해요",
                    fontSize = 28.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp),
                    lineHeight = 1.2.em,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(R.drawable.img_honey_bee_welcome),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(modifier = Modifier.padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(Color(0xFFf5f5f5), shape = CircleShape)
                            .size(42.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_current_location),
                            contentDescription = "",
                            modifier = Modifier.scale(0.7f)
                        )
                    }
                    Column {
                        Text("위치 정보", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("현재 위치 확인하여 재료 배송", fontSize = 12.sp, color = Color(0xFF999999))
                    }
                }
                HorizontalDivider()
                Text(
                    "[동의 및 철회 안내]",
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color(0xFF999999)
                )
                Text(
                    "• 선택 권한은 서비스 사용 중 필요한 시점에 동의를 받고 있습니다.", fontSize = 12.sp,
                    color = Color(0xFF999999),
                    lineHeight = 1.5.em
                )
                Text(
                    "• 선택 권한을 허용하지 않으셔도 기타 서비스 이용은 가능하며, 일부 관련 서비스 이용 시 제한이 있을 수 있습니다.",
                    fontSize = 12.sp,
                    color = Color(0xFF999999),
                    lineHeight = 1.5.em
                )
                Text(
                    "• 허용하신 권한은 휴대폰 [설정]-> [애플리케이션]> [꿀재료] ->\n" +
                            "[권한] 에서 언제든지 철회 가능합니다.",
                    fontSize = 12.sp,
                    color = Color(0xFF999999),
                    lineHeight = 1.5.em
                )
            }
            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PointColor, contentColor = Color.White),
                onClick = onPermissionRequest
            ) {
                Text("확인", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeScreenPreview() {
    HoneyTheme {
        WelcomeScreen { }
    }
}
