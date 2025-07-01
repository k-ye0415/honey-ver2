package com.jin.honey.feature.address.domain.usecase

import com.jin.domain.AddressRepository
import com.jin.model.address.Address
import kotlinx.coroutines.flow.Flow

class GetAddressesUseCase(private val repository: com.jin.domain.AddressRepository) {
    operator fun invoke(): Flow<List<Address>> {
        return repository.fetchSavedAllAddresses()
    }
}
