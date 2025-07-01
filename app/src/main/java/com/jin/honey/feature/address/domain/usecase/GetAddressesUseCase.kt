package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.model.address.Address
import kotlinx.coroutines.flow.Flow

class GetAddressesUseCase(private val repository: AddressRepository) {
    operator fun invoke(): Flow<List<Address>> {
        return repository.fetchSavedAllAddresses()
    }
}
