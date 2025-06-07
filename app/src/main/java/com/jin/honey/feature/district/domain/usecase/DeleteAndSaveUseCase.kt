package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.UserDistrict

class DeleteAndSaveUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(district: UserDistrict): Result<Unit> {
        return repository.deleteAndSaveDistrict(district)
    }
}
