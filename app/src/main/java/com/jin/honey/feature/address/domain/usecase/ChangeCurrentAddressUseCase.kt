package com.jin.honey.feature.address.domain.usecase

import com.jin.domain.AddressRepository
import com.jin.model.address.Address

class ChangeCurrentAddressUseCase(private val addressRepository: com.jin.domain.AddressRepository) {
    suspend operator fun invoke(address: Address): Result<Unit> {
        println("YEJIN 응? ${address.address}\n${address.isLatestAddress}")
        return addressRepository.changeCurrentAddress(address)
    }
}
