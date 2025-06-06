package com.jin.honey.feature.district.data

interface DistrictDataSource {
    suspend fun fetchDistrictsAddress(keyword: String): Result<List<AddressDocument>>
    suspend fun fetchDistrictsKeyword(keyword: String): Result<List<KeywordDocument>>
}
