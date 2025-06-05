package com.jin.honey.feature.district.domain

interface DistrictRepository {
    suspend fun fetchDistrict(keyword: String)
}
