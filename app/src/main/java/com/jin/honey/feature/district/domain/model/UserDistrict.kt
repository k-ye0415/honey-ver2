package com.jin.honey.feature.district.domain.model

data class UserDistrict(
    val id: Int?,
    val districtType: DistrictType,
    val district: District,
    val districtDetail: DistrictDetail
)

enum class DistrictType(val typeName: String) {
    HOME("HOME"),
    CURRENT("CURRENT"),
    HISTORY("HISTORY")
}
