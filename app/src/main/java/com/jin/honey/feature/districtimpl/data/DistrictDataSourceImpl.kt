package com.jin.honey.feature.districtimpl.data

import com.jin.honey.feature.district.data.DistrictDataSource
import com.jin.honey.feature.district.data.AddressDocument
import com.jin.honey.feature.district.data.KakaoMapApi
import com.jin.honey.feature.district.data.KeywordDocument

class DistrictDataSourceImpl(private val kakaoMapApi: KakaoMapApi) : DistrictDataSource {
    override suspend fun fetchDistrictsAddress(keyword: String): Result<List<AddressDocument>> {
        println("YEJIN dataSource")
        val addressResult = kakaoMapApi.searchAddress(keyword)

        val addressList = if (addressResult.isSuccessful) {
            addressResult.body()?.documents ?: emptyList()
        } else {
            emptyList()
        }.also {
            println("YEJIN dataSource addressList : $it")
        }
        return if (addressList.isNotEmpty()) {
            Result.success(addressList)
        } else {
            Result.failure(Exception("Document list is empty"))
        }
    }

    override suspend fun fetchDistrictsKeyword(keyword: String): Result<List<KeywordDocument>> {
        val keywordResult = kakaoMapApi.searchKeyword(keyword)
        val keywordList = if (keywordResult.isSuccessful) {
            keywordResult.body()?.documents ?: emptyList()
        } else {
            emptyList()
        }.also {
            println("YEJIN dataSource keywordList : $it")
        }
        return if (keywordList.isNotEmpty()) {
            Result.success(keywordList)
        } else {
            Result.failure(Exception("Document list is empty"))
        }
    }
}
