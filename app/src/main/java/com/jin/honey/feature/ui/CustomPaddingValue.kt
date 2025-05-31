package com.jin.honey.feature.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun systemBottomBarHeightDp(): Dp {
    return WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
}

@Composable
fun systemTopStatusHeightDp(): Dp {
    return WindowInsets.statusBars.asPaddingValues().calculateTopPadding().value.toInt().dp
}
