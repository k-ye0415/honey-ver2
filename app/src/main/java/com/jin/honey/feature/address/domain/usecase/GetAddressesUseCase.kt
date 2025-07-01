package com.jin.honey.feature.address.domain.usecase

import com.jin.domain.repositories.AddressRepository
import com.jin.domain.model.address.Address
import kotlinx.coroutines.flow.Flow

class GetAddressesUseCase(private val repository: AddressRepository) {
    operator fun invoke(): Flow<List<_root_ide_package_.com.jin.domain.model.address.Address>> {
        return repository.fetchSavedAllAddresses()
    }
}
