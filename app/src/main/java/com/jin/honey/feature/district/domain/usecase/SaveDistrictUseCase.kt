package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.UserAddress

class SaveDistrictUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(userAddress: UserAddress):Result<Unit> {
        return repository.saveUserDistrict(userAddress)
    }
}
