package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.District

class GetDistrictUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(keyword: String): Result<List<District>> {
        println("YEJIN usecase")
        return repository.fetchDistrict(keyword)
    }
}
