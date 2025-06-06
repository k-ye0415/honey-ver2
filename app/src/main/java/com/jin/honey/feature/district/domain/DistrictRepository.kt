package com.jin.honey.feature.district.domain

import com.jin.honey.feature.district.domain.model.District

interface DistrictRepository {
    suspend fun fetchDistrict(keyword: String): Result<List<District>>
}
