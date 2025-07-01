package com.jin.honey.feature.address.data

import com.jin.network.AddressDocument
import com.jin.network.KeywordDocument

interface AddressDataSource {
    suspend fun queryAddressByKeyword(keyword: String): Result<List<com.jin.network.AddressDocument>>
    suspend fun queryPlaceByKeyword(keyword: String): Result<List<com.jin.network.KeywordDocument>>
}
