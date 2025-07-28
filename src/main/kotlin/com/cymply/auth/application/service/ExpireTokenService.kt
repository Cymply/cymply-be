package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.ExpireTokenUseCase
import com.cymply.auth.application.port.out.DeleteRefreshTokenPort
import com.cymply.auth.application.port.out.LoadRefreshTokenPort
import com.cymply.common.util.JwtUtils
import org.springframework.stereotype.Service

/**
 * Refresh Token 만료
 */
@Service
class ExpireTokenService(
    private val jwtUtils: JwtUtils,
    private val loadRefreshTokenPort: LoadRefreshTokenPort,
    private val deleteRefreshTokenPort: DeleteRefreshTokenPort
) : ExpireTokenUseCase {
    override fun expireToken(refreshToken: String) {
        val id = jwtUtils.getId(refreshToken)
        val savedRefreshToken = loadRefreshTokenPort.loadRefreshToken(id)

        if (savedRefreshToken != null) {
            deleteRefreshTokenPort.deleteRefreshToken(id)
        }
    }
}