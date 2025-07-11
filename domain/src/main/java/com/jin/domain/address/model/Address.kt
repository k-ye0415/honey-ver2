package com.jin.domain.address.model

data class Address(
    val id: Int?,
    val isLatestAddress: Boolean,
    val addressTag: AddressTag,
    val address: SearchAddress,
    val addressDetail: String
)

enum class AddressTag(val typeName: String) {
    HOME("HOME"),
    CURRENT("CURRENT");
}
