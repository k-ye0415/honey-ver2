package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.SaveResult
import com.jin.honey.feature.address.domain.model.UserAddress

class SaveAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(userAddress: UserAddress, forceOverride: Boolean): Result<SaveResult> {
        val allAddresses = repository.fetchSavedAllAddresses()
        return when {
            allAddresses.size < 10 -> {
                repository.saveAddress(userAddress)
                Result.success(SaveResult.Saved)
            }

            forceOverride -> {
                val deleteResult = repository.deleteAddress()
                if (deleteResult.isSuccess) {
                    repository.saveAddress(userAddress)
                    Result.success(SaveResult.Saved)
                } else {
                    Result.failure(Exception(deleteResult.exceptionOrNull()?.message.orEmpty()))
                }
            }

            else -> {
                Result.failure(Exception("Address is Full"))
            }
        }
    }
}
