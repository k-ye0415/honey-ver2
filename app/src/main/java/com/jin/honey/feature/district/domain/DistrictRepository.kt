package com.jin.honey.feature.district.domain

import com.jin.honey.feature.district.domain.model.District
import com.jin.honey.feature.district.domain.model.UserDistrict

interface DistrictRepository {
    suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>>
    suspend fun saveUserDistrict(userDistrict: UserDistrict): Result<Unit>
    suspend fun findUserDistrict(): Result<List<UserDistrict>>
}
