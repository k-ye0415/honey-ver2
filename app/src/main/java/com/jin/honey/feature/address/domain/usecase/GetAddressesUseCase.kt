package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.UserAddress

class GetAddressesUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(): Result<List<UserAddress>> {
        return repository.findAddresses()
    }
}
