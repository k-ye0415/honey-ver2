package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address

class SearchAddressUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(keyword: String): Result<List<Address>> {
        return repository.searchDistrictsByKeyword(keyword)
    }
}
