package com.jin.honey.feature.address.data

import com.jin.honey.feature.address.data.model.AddressDocument
import com.jin.honey.feature.address.data.model.KeywordDocument

interface AddressDataSource {
    suspend fun queryAddressByKeyword(keyword: String): Result<List<AddressDocument>>
    suspend fun queryPlaceByKeyword(keyword: String): Result<List<KeywordDocument>>
}
