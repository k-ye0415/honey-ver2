package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.Address

class SearchAddressUseCase(private val repository: DistrictRepository) {
    suspend operator fun invoke(keyword: String): Result<List<Address>> {
        val searchAddress = repository.searchAddressByKeyword(keyword)
        return if (searchAddress.isEmpty()) Result.failure(Exception("Search address is empty"))
        else Result.success(searchAddress)
    }
}
