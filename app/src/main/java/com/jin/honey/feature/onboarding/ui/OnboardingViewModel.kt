package com.jin.honey.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.datastore.PreferencesRepository
import com.jin.honey.feature.food.domain.usecase.SyncAllMenuUseCase
import com.jin.honey.feature.recipe.domain.SyncRecipesUseCase
import com.jin.honey.feature.review.domain.SyncReviewsUseCase
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val repository: PreferencesRepository,
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
