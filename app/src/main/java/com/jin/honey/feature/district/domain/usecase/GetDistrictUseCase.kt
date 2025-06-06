package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.UserDistrict

class GetDistrictUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(): Result<List<UserDistrict>> {
        return repository.findUserDistrict()
    }
}
