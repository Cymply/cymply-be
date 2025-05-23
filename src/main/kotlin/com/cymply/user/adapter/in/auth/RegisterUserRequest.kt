package com.cymply.user.adapter.`in`.auth

data class RegisterUserRequest(
    val email: String,
    val sub: String?,
    val provider: String?,
    val name: String?,
    val nickname: String?,
    val thumbnail: String?
)