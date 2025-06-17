package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.OAuth2LoginCommand
import com.cymply.auth.application.port.`in`.OAuth2LoginUseCase
import com.cymply.auth.application.port.out.SaveTokenPort
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
    private val getUserUseCase: GetUserUseCase,
    private val jwtUtils: JwtUtils,
    private val saveTokenPort: SaveTokenPort
) : OAuth2LoginUseCase {
    override fun oAuth2Login(command: OAuth2LoginCommand): AuthenticationToken {
        val user = getUserUseCase.getActiveUser(command.provider, command.sub)

        if (user == null) {
            val claims = command.getAttributes() + mapOf("scope" to "user:signup")
            val accessToken = jwtUtils.generate(claims, TokenExpirePolicy.TEMPORAL)
            return AuthenticationToken(accessToken, TokenExpirePolicy.TEMPORAL, accessToken, TokenExpirePolicy.TEMPORAL)
        }

        val principal = AuthenticatedPrincipal.of(user.id, user.email, user.nickname, user.role.name)
        val accessToken = jwtUtils.generate(principal.getAttributes(), TokenExpirePolicy.ACCESS)
        val refreshToken = jwtUtils.generate(principal.getAttributes(), TokenExpirePolicy.REFRESH)
        saveTokenPort.saveRefreshToken(refreshToken)

        return AuthenticationToken(accessToken, TokenExpirePolicy.ACCESS, refreshToken, TokenExpirePolicy.REFRESH)
    }
}