package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.Address

class GetLatestAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(): Result<Address> {
        val address = repository.findLatestAddress()
        return if (address == null) Result.failure(Exception("Address is null"))
        else Result.success(address)
    }
}
