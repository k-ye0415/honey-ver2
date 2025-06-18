package com.jin.honey.feature.address.domain.usecase

import com.jin.honey.feature.address.domain.AddressRepository
import com.jin.honey.feature.address.domain.model.SaveResult
import com.jin.honey.feature.address.domain.model.UserAddress

class SaveAddressUseCase(private val repository: AddressRepository) {
    suspend operator fun invoke(userAddress: UserAddress, forceOverride: Boolean): Result<SaveResult> {
        return repository.findAddresses()
            .map { addresses: List<UserAddress> ->
                when {
                    addresses.size < 10 -> {
                        repository.saveAddress(userAddress)
                        SaveResult.Saved
                    }

                    forceOverride -> {
                        val deleteResult = repository.deleteAddress()
                        if (deleteResult.isSuccess) {
                            repository.saveAddress(userAddress)
                            SaveResult.Saved
                        } else {
                            SaveResult.Error(deleteResult.exceptionOrNull()?.message.orEmpty())
                        }
                    }

                    else -> {
                        SaveResult.Full("Address is Full")
                    }
                }
            }
            .onFailure {
                if (it.message == "Address List is empty") {
                    repository.saveAddress(userAddress)
                    SaveResult.Saved
                }
            }
    }

}
