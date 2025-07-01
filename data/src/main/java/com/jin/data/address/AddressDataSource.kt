package com.jin.data.address

import com.jin.network.kakao.AddressDocument
import com.jin.network.kakao.KeywordDocument

interface AddressDataSource {
    suspend fun queryAddressByKeyword(keyword: String): Result<List<AddressDocument>>
    suspend fun queryPlaceByKeyword(keyword: String): Result<List<KeywordDocument>>
}
