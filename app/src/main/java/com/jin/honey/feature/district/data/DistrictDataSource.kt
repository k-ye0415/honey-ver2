package com.jin.honey.feature.district.data

import com.jin.honey.feature.district.data.model.AddressDocument
import com.jin.honey.feature.district.data.model.KeywordDocument

interface DistrictDataSource {
    suspend fun queryAddressByKeyword(keyword: String): Result<List<AddressDocument>>
    suspend fun queryPlaceByKeyword(keyword: String): Result<List<KeywordDocument>>
}
