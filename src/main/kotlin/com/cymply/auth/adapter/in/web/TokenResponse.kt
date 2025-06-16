package com.cymply.auth.adapter.`in`.web

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
) {
}