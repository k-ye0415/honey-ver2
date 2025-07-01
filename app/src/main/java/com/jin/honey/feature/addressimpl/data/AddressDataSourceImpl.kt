package com.jin.honey.feature.addressimpl.data

import com.jin.honey.feature.address.data.AddressDataSource
import com.jin.network.kakao.KakaoMapApi
import com.jin.network.kakao.AddressDocument
import com.jin.network.kakao.KakaoResponse
import com.jin.network.kakao.KeywordDocument
import retrofit2.Response

class AddressDataSourceImpl(private val kakaoMapApi: KakaoMapApi) : AddressDataSource {

    override suspend fun queryAddressByKeyword(keyword: String): Result<List<AddressDocument>> {
        return processKakaoResponse(
            response = kakaoMapApi.searchAddress(keyword),
            emptyMessage = "Address document list is empty"
        )
    }

    override suspend fun queryPlaceByKeyword(keyword: String): Result<List<KeywordDocument>> {
        return processKakaoResponse(
            response = kakaoMapApi.searchKeyword(keyword),
            emptyMessage = "Keyword document list is empty"
        )
    }

    private inline fun <T> processKakaoResponse(
        response: Response<KakaoResponse<T>>,
        emptyMessage: String
    ): Result<List<T>> {
        return if (response.isSuccessful) {
            val list = response.body()?.documents.orEmpty()
            if (list.isNotEmpty()) {
                Result.success(list)
            } else {
                Result.failure(Exception(emptyMessage))
            }
        } else {
            Result.failure(Exception("Kakao API Error: ${response.code()} ${response.message()}"))
        }
    }
}
