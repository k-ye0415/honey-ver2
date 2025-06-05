package com.jin.honey.feature.districtimpl.data

import com.jin.honey.feature.district.NaverMapApi
import com.jin.honey.feature.district.data.DistrictDataSource

class DistrictDataSourceImpl(private val naverMapApi: NaverMapApi) : DistrictDataSource {
    override suspend fun fetchDistricts(keyword: String) {
        println("YEJIN dataSource")
        val result = naverMapApi.searchAddress(query = keyword, display = 10, start = 1, sort = "sim")
        if (result.isSuccessful) {
            val success = result.body()?.items.orEmpty()
            println("YEJIN dataSource success : $success")
        } else {
            val error = result.code()
            println("YEJIN dataSource error : $error")
        }
    }
}
//[NaverAddressItem(title=낙원동악기상가<b>서</b>측주차장, address=서울특별시 종로구 낙원동 288, roadAddress=서울특별시 종로구 삼일대로 428, mapx=1269876625, mapy=375727249), NaverAddressItem(title=회계법인<b>서</b>본, address=서울특별시 중구 서소문동 39-1 3층, roadAddress=서울특별시 중구 서소문로11길 50 3층, mapx=1269730854, mapy=375648049), NaverAddressItem(title=세계보건기구 <b>서</b>태평양지역 아시아태평양 환경보건센터, address=서울특별시 종로구 서린동 63 11층 사무실, roadAddress=서울특별시 종로구 종로 38 11층 사무실, mapx=1269814792, mapy=375699205), NaverAddressItem(title=<b>서</b>앤컴퍼니, address=서울특별시 종로구 신문로1가 115 콘코디언빌딩 3층 (우: 03185), roadAddress=서울특별시 종로구 새문안로 76 콘코디언빌딩 3층 (우: 03185), mapx=1269731965, mapy=375697843), NaverAddressItem(title=대구<b>서</b>씨대종회, address=서울특별시 종로구 수송동 85, roadAddress=서울특별시 종로구 율곡로2길 7, mapx=1269812711, mapy=375750219)]
