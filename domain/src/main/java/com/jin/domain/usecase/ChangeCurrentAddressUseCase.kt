package com.jin.domain.usecase

import com.jin.domain.address.model.Address
import com.jin.domain.address.AddressRepository

class ChangeCurrentAddressUseCase(private val addressRepository: AddressRepository) {
    suspend operator fun invoke(address: Address): Result<Unit> {
        println("YEJIN 응? ${address.address}\n${address.isLatestAddress}")
        return addressRepository.changeCurrentAddress(address)
    }
}
