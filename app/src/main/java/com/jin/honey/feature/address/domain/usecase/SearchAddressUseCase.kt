package com.jin.honey.feature.address.domain.usecase

import com.jin.domain.AddressRepository
import com.jin.model.address.SearchAddress

class SearchAddressUseCase(private val repository: com.jin.domain.AddressRepository) {
    suspend operator fun invoke(keyword: String): Result<List<SearchAddress>> {
        val searchAddresses = repository.searchAddressByKeyword(keyword)
        return if (searchAddresses.isEmpty()) Result.failure(Exception("Address Search list is emtpy"))
        else Result.success(searchAddresses)
    }
}
