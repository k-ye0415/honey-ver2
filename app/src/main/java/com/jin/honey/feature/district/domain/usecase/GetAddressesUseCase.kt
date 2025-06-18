package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.UserAddress

class GetAddressesUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(): Result<List<UserAddress>> {
        val allAddresses = repository.fetchSavedAllAddresses()
        return if (allAddresses.isEmpty()) Result.failure(Exception("Address is empty"))
        else Result.success(allAddresses)
    }
}
