package com.jin.honey.feature.district.domain.usecase

import com.jin.honey.feature.district.domain.DistrictRepository
import com.jin.honey.feature.district.domain.model.SaveResult
import com.jin.honey.feature.district.domain.model.UserAddress

class SaveDistrictUseCase(private val repository: DistrictRepository) {
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
                        SaveResult.Full("District is Full")
                    }
                }
            }
            .onFailure {
                if (it.message == "District List is empty") {
                    repository.saveAddress(userAddress)
                    SaveResult.Saved
                }
            }
    }

}
