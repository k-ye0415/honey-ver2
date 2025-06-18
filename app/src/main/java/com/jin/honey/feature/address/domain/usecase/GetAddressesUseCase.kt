package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.Address

class GetAddressesUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(): Result<List<Address>> {
        val addresses = repository.fetchSavedAllAddresses()
        return if (addresses.isEmpty()) Result.failure(Exception("Address is empty"))
        else Result.success(addresses)
    }
}
