package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.domain.DistrictRepository

class DistrictRepositoryImpl(private val districtDataSource: DistrictDataSource) : DistrictRepository {
    override suspend fun fetchDistrict(keyword: String) {
        println("YEJIN repository")
        districtDataSource.fetchDistricts(keyword)
    }
}
