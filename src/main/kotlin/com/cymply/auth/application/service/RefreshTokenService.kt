package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.RefreshTokenUseCase
import com.cymply.auth.application.port.out.LoadRefreshTokenPort
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import com.cymply.auth.domain.AuthenticatedPrincipal
import com.cymply.common.util.JwtUtils
import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.stereotype.Service

/**
 * 만료된 Access Token 재발급
 */
@Service
class RefreshTokenService(
    private val jwtUtils: JwtUtils,
    private val getUserUseCase: GetUserUseCase,
    private val loadRefreshTokenPort: LoadRefreshTokenPort,
    private val saveTokenPort: SaveRefreshTokenPort
) : RefreshTokenUseCase {
    override fun refreshToken(refreshToken: String): AuthenticationToken {
        val loadedRefreshToken = loadRefreshTokenPort.loadRefreshToken(refreshToken)
            ?: throw IllegalArgumentException("Not found RefreshToken")

        val userId = jwtUtils.extractUserId(loadedRefreshToken)
        val user = getUserUseCase.getActiveUserOrElseThrow(userId)
        val principal = AuthenticatedPrincipal.of(user.id, user.email, user.nickname, user.role.name)

        val newAccessToken = jwtUtils.generate(principal.getAttributes(), TokenExpirePolicy.ACCESS)
        val newRefreshToken = jwtUtils.generate(principal.getAttributes(), TokenExpirePolicy.REFRESH)
        saveTokenPort.saveRefreshToken(newRefreshToken, TokenExpirePolicy.REFRESH)

        return AuthenticationToken(
            newAccessToken, TokenExpirePolicy.ACCESS,
            newRefreshToken, TokenExpirePolicy.REFRESH
        )
    }
}