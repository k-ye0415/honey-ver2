package com.jin.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.domain.launch.LaunchRepository
import com.jin.domain.usecase.SyncAllMenuUseCase
import com.jin.domain.usecase.SyncRecipesUseCase
import com.jin.domain.usecase.SyncReviewsUseCase
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val repository: LaunchRepository,
    private val syncAllMenuUseCase: SyncAllMenuUseCase,
    private val syncReviewsUseCase: SyncReviewsUseCase,
    private val syncRecipesUseCase: SyncRecipesUseCase
) : ViewModel() {
    private var isFirstLaunch = true

    init {
        checkIfFirstLaunch()
        syncAllMenu()
    }

    private fun syncAllMenu() {
        viewModelScope.launch {
            syncAllMenuUseCase()
            syncReviewsUseCase()
            syncRecipesUseCase()
        }
    }

    fun onLocationPermissionGranted() {
        viewModelScope.launch {
            if (isFirstLaunch) {
                repository.completeFirstLaunch()
            }
        }
    }

    private fun checkIfFirstLaunch() {
        viewModelScope.launch {
            isFirstLaunch = repository.isFirstLaunch()
        }
    }
}
