package com.cymply.auth.application.port.out

interface DeleteRefreshTokenPort {
    fun deleteRefreshToken(id: String): Boolean
}