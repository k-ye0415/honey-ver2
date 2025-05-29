package com.jin.honey.feature.ingredient.ui

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jin.honey.feature.ui.state.UiState

@Composable
fun IngredientScreen(viewModel: IngredientViewModel, menuName: String) {
    val menu by viewModel.menu.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchMenu(menuName)
    }

    when (val state = menu) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> Text(state.data.toString())
        is UiState.Error -> CircularProgressIndicator()
    }
}
