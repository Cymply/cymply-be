package com.cymply.auth.application.port.out

interface SaveRefreshTokenPort {
    fun saveRefreshToken(refreshToken: String, ttl: Long)
}