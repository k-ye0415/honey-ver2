package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.SearchAddress

class SearchAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(keyword: String): Result<List<SearchAddress>> {
        return repository.searchAddressByKeyword(keyword)
    }
}
