package com.cymply.auth.adapter.`in`.web

import com.cymply.auth.adapter.`in`.dto.RefreshTokenRequest
import com.cymply.auth.adapter.`in`.dto.TokenResponse
import com.cymply.auth.application.port.`in`.ExpireTokenUseCase
import com.cymply.auth.application.port.`in`.RefreshTokenUseCase
import com.cymply.auth.application.port.`in`.ReissueTokenCommand
import com.cymply.auth.application.port.`in`.ReissueTokenUseCase
import com.cymply.common.response.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val reissueTokenUseCase: ReissueTokenUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val expireTokenUseCase: ExpireTokenUseCase
) : AuthApiController {
    override fun oAuth2Login(
        provider: String
    ) {
        throw UnsupportedOperationException("Delegate Security client")
    }

    override fun oAuth2SignupSuccess(
        @AuthenticationPrincipal jwt: Jwt
    ): ApiResponse<TokenResponse> {
        val result = reissueTokenUseCase.reissueToken(
            ReissueTokenCommand(
                sub = jwt.claims["sub"] as String,
                provider = jwt.claims["provider"] as String,
            )
        )

        val response = TokenResponse(result.accessToken, result.expiresIn, result.refreshToken)
        return ApiResponse.success(content = response)
    }

    override fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): ApiResponse<TokenResponse> {
        val result = refreshTokenUseCase.refreshToken(request.refreshToken)
        val response = TokenResponse(result.accessToken, result.expiresIn, result.refreshToken)
        return ApiResponse.success(content = response)
    }

    override fun logout(
        @RequestBody request: RefreshTokenRequest
    ): ApiResponse<Unit> {
        expireTokenUseCase.expireToken(request.refreshToken)
        return ApiResponse.success(content = Unit)
    }
}