package com.cymply.auth.application.port.out

interface LoadRefreshTokenPort {
    fun loadRefreshToken(key: String): String?
}