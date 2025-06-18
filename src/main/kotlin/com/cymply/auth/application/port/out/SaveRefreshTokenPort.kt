package com.cymply.auth.application.port.out

interface SaveRefreshTokenPort {
    fun saveRefreshToken(id: String, refreshToken: String, ttl: Long)
}