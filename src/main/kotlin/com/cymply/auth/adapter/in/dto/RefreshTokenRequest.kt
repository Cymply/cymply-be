package com.cymply.auth.adapter.`in`.dto

data class RefreshTokenRequest(
    val refreshToken: String,
    val grantType: String? = "refreshToken"
)