package com.jin.domain.launch

interface LaunchRepository {
    suspend fun completeFirstLaunch()
    suspend fun isFirstLaunch(): Boolean
}
