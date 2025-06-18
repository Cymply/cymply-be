package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.ReissueTokenCommand
import com.cymply.auth.application.port.`in`.ReissueTokenUseCase
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import com.cymply.auth.domain.AuthenticatedPrincipal
import com.cymply.common.util.JwtUtils
import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.stereotype.Service

/**
 * OAuth2 회원가입 후 정상 토큰 발급
 */
@Service
class ReissueTokenService(
    private val jwtUtils: JwtUtils,
    private val getUserUseCase: GetUserUseCase,
    private val saveTokenPort: SaveRefreshTokenPort
) : ReissueTokenUseCase {
    override fun reissueToken(command: ReissueTokenCommand): AuthenticationToken {
        val user = getUserUseCase.getActiveUser(sub = command.sub, provider = command.provider)
            ?: throw IllegalArgumentException("The user ${command.sub} does not exist.")

        val principal = AuthenticatedPrincipal.of(user.id, user.email, user.nickname, user.role.name)
        val accessToken = jwtUtils.generate(principal.getAttributes(), TokenExpirePolicy.ACCESS)
        val refreshToken = jwtUtils.generate(principal.getAttributes(), TokenExpirePolicy.REFRESH)

        saveTokenPort.saveRefreshToken(refreshToken, TokenExpirePolicy.REFRESH)
        return AuthenticationToken(
            accessToken, TokenExpirePolicy.ACCESS, refreshToken, TokenExpirePolicy.REFRESH, scopes = principal.scopes
        )
    }
}