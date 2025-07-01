package com.jin.domain.usecase

import com.jin.domain.address.model.Address
import com.jin.domain.address.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetAddressesUseCase(private val repository: AddressRepository) {
    operator fun invoke(): Flow<List<Address>> {
        return repository.fetchSavedAllAddresses()
    }
}
