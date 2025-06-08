package com.jin.honey.feature.district.domain

import com.jin.honey.feature.district.domain.model.Address
import com.jin.honey.feature.district.domain.model.UserAddress

interface DistrictRepository {
    /**
     * 사용자가 입력한 Text 로 주소 검색
     *
     * @param keyword
     * @return
     */
    suspend fun searchAddressByKeyword(keyword: String): Result<List<Address>>

    /**
     * 저장된 모든 주소 가져오기
     *
     * @return
     */
    suspend fun findAddresses(): Result<List<UserAddress>>

    /**
     * 주소 저장
     *
     * @param userAddress
     * @return
     */
    suspend fun saveAddress(userAddress: UserAddress): Result<Unit>

    /**
     * 주소 삭제
     *
     * @return
     */
    suspend fun deleteAddress(): Result<Unit>

    /**
     * 가장 최근 지정된 주소 가져오기
     *
     * @return
     */
    suspend fun findLatestAddress(): Result<UserAddress>
}
