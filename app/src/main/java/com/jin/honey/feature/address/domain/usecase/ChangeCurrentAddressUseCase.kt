package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.Address

class ChangeCurrentAddressUseCase(private val addressRepository: AddressRepository) {
    suspend operator fun invoke(address: Address): Result<Unit> {
        println("YEJIN 응? ${address.address}\n${address.isLatestAddress}")
        return addressRepository.changeCurrentAddress(address)
    }
}
