package com.cymply.auth.application.port.out

interface LoadRefreshTokenPort {
    fun loadRefreshToken(id: String): String?
}