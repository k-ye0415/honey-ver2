package com.jin.domain.usecase

import com.jin.domain.address.model.Address
import com.jin.domain.SaveResult
import com.jin.domain.address.AddressRepository
import kotlinx.coroutines.flow.firstOrNull

class SaveAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(
        address: Address,
        forceOverride: Boolean
    ): Result<SaveResult> {
        val allAddresses = repository.fetchSavedAllAddresses().firstOrNull() ?: emptyList()
        return when {
            allAddresses.size < 10 -> {
                repository.saveAddress(address)
                Result.success(SaveResult.Saved)
            }

            forceOverride -> {
                val deleteResult = repository.deleteAddress()
                if (deleteResult.isSuccess) {
                    repository.saveAddress(address)
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
