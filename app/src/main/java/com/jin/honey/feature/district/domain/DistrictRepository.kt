package com.jin.honey.feature.district.domain

import com.jin.honey.feature.district.domain.model.District

interface DistrictRepository {
    suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>>
}
