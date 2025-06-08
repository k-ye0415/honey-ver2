package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.UserAddress

class GetLatestAddressUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(): Result<UserAddress> {
        return repository.findLatestAddress()
    }
}
