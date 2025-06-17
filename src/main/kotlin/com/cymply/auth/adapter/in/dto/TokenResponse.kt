package com.cymply.auth.adapter.`in`.dto

data class TokenResponse(
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
    val tokenType: String = "Bearer"
)