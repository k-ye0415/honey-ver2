package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository

class GetDistrictUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(keyword: String) {
        println("YEJIN usecase")
        repository.fetchDistrict(keyword)
    }
}
