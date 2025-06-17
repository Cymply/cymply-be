package com.cymply.auth.application.service

import com.cymply.auth.adapter.`in`.dto.TokenResponse
import com.cymply.auth.application.port.out.LoadTokenPort
import com.cymply.auth.application.port.out.SaveTokenPort
import com.cymply.common.util.JwtUtils
import org.springframework.stereotype.Service

/**
 * 만료된 Access Token 재발급
 */
@Service
class RefreshTokenService(
    private val jwtUtils: JwtUtils,
    private val loadTokenPort: LoadTokenPort,
    private val saveTokenPort: SaveTokenPort
) {
    fun refreshToken(token: String): TokenResponse {
        TODO()
    }
}