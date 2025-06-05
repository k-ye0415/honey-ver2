package com.jin.honey.feature.district.data

interface DistrictDataSource {
    suspend fun fetchDistricts(keyword: String)
}
