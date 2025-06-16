package com.cymply.auth.application.service

data class OAuth2LoginResult(
    val accessToken: String,
    val refreshToken: String,
    val authorities: List<String>,
    val expiresIn: Long?,
    val tokenType: String? = "Bearer"
)