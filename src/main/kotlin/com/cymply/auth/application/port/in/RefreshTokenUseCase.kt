package com.cymply.auth.application.port.`in`

import com.cymply.auth.application.service.AuthenticationToken

interface RefreshTokenUseCase {
    fun refreshToken(refreshToken: String): AuthenticationToken
}