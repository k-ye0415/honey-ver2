package com.jin.honey.feature.address.domain

import com.jin.honey.feature.address.domain.model.Address
import com.jin.honey.feature.address.domain.model.SearchAddress

interface AddressRepository {
    /**
     * 사용자가 입력한 Text 로 주소 검색
     *
     * @param keyword
     * @return
     */
    suspend fun searchAddressByKeyword(keyword: String): List<SearchAddress>

    /**
     * 저장된 모든 주소 가져오기
     *
     * @return
     */
    suspend fun fetchSavedAllAddresses(): List<Address>

    /**
     * 주소 저장
     *
     * @param userAddress
     * @return
     */
    suspend fun saveAddress(userAddress: Address): Result<Unit>

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
    suspend fun findLatestAddress(): Address?
}
