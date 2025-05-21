package com.cymply.user.adapter.`in`.web

data class UserDto(
    val id: Long,
    val email: String,
    val nickname: String? = null
)