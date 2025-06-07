package com.jin.honey.feature.district.domain

import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress

interface DistrictRepository {
    suspend fun searchDistrictsByKeyword(keyword: String): Result<List<Address>>
    suspend fun saveUserDistrict(userAddress: UserAddress): Result<Unit>
    suspend fun findUserDistrict(): Result<List<UserAddress>>
    suspend fun deleteAndSaveDistrict(userAddress: UserAddress): Result<Unit>
}
