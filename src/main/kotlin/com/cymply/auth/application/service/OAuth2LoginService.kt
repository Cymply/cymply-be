package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.OAuth2LoginCommand
import com.cymply.auth.application.port.`in`.OAuth2LoginUseCase
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import com.cymply.common.util.JwtUtils
import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

/**
 * OAuth2 로그인
 * 비회원인 경우 임시 토큰을 발급합니다.
 * 회원인 경우 정상 토큰을 발급합니다.
 */
@Service
class OAuth2LoginService(
    private val jwtUtils: JwtUtils,
    private val getUserUseCase: GetUserUseCase,
    private val saveRefreshTokenPort: SaveRefreshTokenPort
) : OAuth2LoginUseCase {
    companion object {
        const val ACCESS_TOKEN_EXPIRES = 1800_000.toLong()  // 30 min
        const val REFRESH_TOKEN_EXPIRES = 604_800_000.toLong()  // 7 days
        const val TEMPORAL_TOKEN_EXPIRES = 900_000.toLong()  // 15 min
    }

    override fun login(command: OAuth2LoginCommand): OAuth2LoginResult {
        val user = getUserUseCase.getActiveUser(command.provider, command.sub)

        if (user == null) {
            val claims = command.getAttributes() + mapOf("role" to "PENDING_USER")
            val accessToken = jwtUtils.generate(claims, TEMPORAL_TOKEN_EXPIRES)
            return OAuth2LoginResult(
                accessToken = accessToken,
                refreshToken = accessToken,
                authorities = listOf("PENDING_USER"),
                expiresIn = TEMPORAL_TOKEN_EXPIRES
            )
        }

        val claims = mapOf(
            "id" to user.id,
            "email" to user.email,
            "nickname" to user.nickname,
            "role" to user.role.name
        )

        val accessToken = jwtUtils.generate(claims, ACCESS_TOKEN_EXPIRES)
        val refreshToken = jwtUtils.generate(claims, REFRESH_TOKEN_EXPIRES)
        saveRefreshTokenPort.saveRefreshToken(refreshToken)

        return OAuth2LoginResult(
            accessToken = accessToken,
            refreshToken = refreshToken,
            authorities = listOf(user.role.name),
            expiresIn = ACCESS_TOKEN_EXPIRES
        )
    }
}