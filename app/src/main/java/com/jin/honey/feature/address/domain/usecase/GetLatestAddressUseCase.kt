package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.UserAddress

class GetLatestAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(): Result<UserAddress> {
        return repository.findLatestAddress()
    }
}
