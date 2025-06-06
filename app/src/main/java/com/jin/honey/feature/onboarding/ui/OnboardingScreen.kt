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
import androidx.compose.ui.res.stringResource
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
import com.jin.honey.ui.theme.OnboardingDescTextColor
import com.jin.honey.ui.theme.OnboardingIconBackgroundColor
import com.jin.honey.ui.theme.OnboardingImageBackgroundColor
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
                    .background(OnboardingImageBackgroundColor)
            ) {
                Text(
                    text = stringResource(R.string.onboarding_title),
                    fontSize = 28.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 30.dp),
                    lineHeight = 1.2.em,
                    fontWeight = FontWeight.Bold
                )
                Image(
                    painter = painterResource(R.drawable.img_honey_bee_welcome),
                    contentDescription = stringResource(R.string.onboarding_img_desc),
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.BottomEnd)
                )
            }
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Row(
                    modifier = Modifier.padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .background(OnboardingIconBackgroundColor, shape = CircleShape)
                            .size(42.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_current_location),
                            contentDescription = stringResource(R.string.onboarding_location_icon_desc),
                            modifier = Modifier.scale(0.7f)
                        )
                    }
                    Column {
                        Text(
                            text = stringResource(R.string.onboarding_permission_location),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.onboarding_permission_location_description),
                            fontSize = 12.sp,
                            color = OnboardingDescTextColor
                        )
                    }
                }
                HorizontalDivider()
                Text(
                    text = stringResource(R.string.onboarding_consent_title),
                    modifier = Modifier.padding(top = 16.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = OnboardingDescTextColor
                )
                Text(
                    text = stringResource(R.string.onboarding_consent_description_1),
                    fontSize = 12.sp,
                    color = OnboardingDescTextColor,
                    lineHeight = 1.5.em
                )
                Text(
                    text = stringResource(R.string.onboarding_consent_description_2),
                    fontSize = 12.sp,
                    color = OnboardingDescTextColor,
                    lineHeight = 1.5.em
                )
                Text(
                    text = stringResource(R.string.onboarding_consent_description_3),
                    fontSize = 12.sp,
                    color = OnboardingDescTextColor,
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
                Text(text = stringResource(R.string.onboarding_button_confirm), fontWeight = FontWeight.Bold)
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
