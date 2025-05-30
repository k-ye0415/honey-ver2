package com.jin.honey.feature.onboarding.ui

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

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

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.LightGray)
            ) {
                Text("이미지")
            }
            Row {
                Box(
                    modifier = Modifier
                        .background(Color.LightGray, shape = CircleShape)
                        .size(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "")
                }
                Column {
                    Text("위치 정보")
                    Text("현재 위치 확인이 필요해요")
                }
            }
            HorizontalDivider()
            Text(
                """[동의 및 철회 안내]
• 선택 권한은 서비스 사용 중 필요한 시점에 동의를 받고 있습니다.
• 선택 권한을 허용하지 않으셔도 기타 서비스 이용은 가능하며, 일부 관련 서비스 이용 시 제한이 있을 수 있습니다.
• 허용하신 권한은 휴대폰 [설정]-> [애플리케이션]> [꿀재료] ->
[권한] 에서 언제든지 철회 가능합니다."""
            )
            Button({ composePermissionState.launchPermissionRequest() }) {
                Text("확인")
            }
        }
    }
}
