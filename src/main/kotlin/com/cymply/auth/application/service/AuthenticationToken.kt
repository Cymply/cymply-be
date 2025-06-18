package com.cymply.auth.application.service

data class AuthenticationToken(
    val accessToken: String,
    val expiresIn: Long,
    val refreshToken: String,
    val refreshToKenExpiresIn: Long,
    val tokenType: String? = "Bearer",
    val scopes: List<String>? = mutableListOf()
)