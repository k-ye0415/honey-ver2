package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.AddressItem

interface DistrictDataSource {
    suspend fun fetchDistricts(keyword: String): Result<List<AddressItem>>
}
