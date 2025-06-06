package com.jin.honey.feature.districtimpl.data

import com.jin.honey.feature.district.data.NaverMapApi
import com.jin.honey.feature.district.data.DistrictDataSource
import com.jin.honey.feature.district.data.model.AddressItem

class DistrictDataSourceImpl(private val naverMapApi: NaverMapApi) : DistrictDataSource {
    override suspend fun fetchDistricts(keyword: String): Result<List<AddressItem>> {
        println("YEJIN dataSource")
        val result = naverMapApi.searchAddress(query = keyword, display = 10, start = 1, sort = "sim")
        return if (result.isSuccessful) {
            val success = result.body()?.items.orEmpty()
            println("YEJIN dataSource success : $success")
            Result.success(success)
        } else {
            val error = result.code()
            println("YEJIN dataSource error : $error")
            Result.failure(Exception(error.toString()))
        }
    }
}
