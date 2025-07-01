package com.jin.honey.feature.address.domain.usecase

import com.jin.domain.repositories.AddressRepository
import com.jin.domain.model.address.Address

class ChangeCurrentAddressUseCase(private val addressRepository: AddressRepository) {
    suspend operator fun invoke(address: _root_ide_package_.com.jin.domain.model.address.Address): Result<Unit> {
        println("YEJIN 응? ${address.address}\n${address.isLatestAddress}")
        return addressRepository.changeCurrentAddress(address)
    }
}
