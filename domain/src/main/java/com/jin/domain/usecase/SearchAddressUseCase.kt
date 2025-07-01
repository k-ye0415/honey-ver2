package com.jin.domain.usecase

import com.jin.domain.repositories.AddressRepository
import com.jin.domain.model.address.SearchAddress

class SearchAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(keyword: String): Result<List<SearchAddress>> {
        val searchAddresses = repository.searchAddressByKeyword(keyword)
        return if (searchAddresses.isEmpty()) Result.failure(Exception("Address Search list is emtpy"))
        else Result.success(searchAddresses)
    }
}
