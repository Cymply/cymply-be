package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.OAuth2LoginCommand
import com.cymply.auth.application.port.`in`.OAuth2LoginUseCase
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import com.cymply.auth.domain.AuthenticatedPrincipal
import com.cymply.common.util.JwtUtils
import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.stereotype.Service

/**
 * OAuth2 로그인
 * 비회원인 경우 임시 토큰을 발급합니다.
 * 회원인 경우 정상 토큰을 발급합니다.
 */
@Service
class OAuth2LoginService(
    private val jwtUtils: JwtUtils,
    private val expirePolicy: TokenExpirePolicy,
    private val getUserUseCase: GetUserUseCase,
    private val saveTokenPort: SaveRefreshTokenPort
) : OAuth2LoginUseCase {
    override fun oAuth2Login(command: OAuth2LoginCommand): AuthenticationToken {
        val user = getUserUseCase.getActiveUser(command.provider, command.sub)
        // 비회원인 경우
        if (user == null) {
            val scope = "user:signup"
            val claims = command.getAttributes() + mapOf("scope" to scope)
            val accessToken = jwtUtils.generate(claims, expirePolicy.temporal)

            return AuthenticationToken(
                accessToken, expirePolicy.temporal, accessToken, expirePolicy.temporal, scopes = listOf(scope)
            )
        }

        // 회원인 경우
        val principal = AuthenticatedPrincipal.of(user.id, user.email, user.nickname, user.role.name)
        val accessToken = jwtUtils.generate(principal.getAttributes(), expirePolicy.access)
        val refreshToken = jwtUtils.generate(principal.getAttributes(), expirePolicy.access)
        saveTokenPort.saveRefreshToken(jwtUtils.getId(refreshToken), refreshToken, expirePolicy.refresh)

        return AuthenticationToken(
            accessToken, expirePolicy.refresh, refreshToken, expirePolicy.refresh, scopes = principal.scopes
        )
    }
}