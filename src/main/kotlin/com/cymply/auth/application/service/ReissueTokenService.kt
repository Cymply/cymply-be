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
    private val expirePolicy: TokenExpirePolicy,
    private val getUserUseCase: GetUserUseCase,
    private val saveTokenPort: SaveRefreshTokenPort
) : ReissueTokenUseCase {
    override fun reissueToken(command: ReissueTokenCommand): AuthenticationToken {
        val user = getUserUseCase.getActiveUser(command.provider, command.sub)
            ?: throw IllegalArgumentException("Not found user: ${command.sub}")

        val principal = AuthenticatedPrincipal.of(user.id, user.email, user.nickname, user.role.name)
        val accessToken = jwtUtils.generate(principal.getAttributes(), expirePolicy.access)
        val refreshToken = jwtUtils.generate(principal.getAttributes(), expirePolicy.refresh)
        saveTokenPort.saveRefreshToken(jwtUtils.getId(refreshToken), refreshToken, expirePolicy.refresh)

        return AuthenticationToken(
            accessToken, expirePolicy.access, refreshToken, expirePolicy.refresh, scopes = principal.scopes
        )
    }
}