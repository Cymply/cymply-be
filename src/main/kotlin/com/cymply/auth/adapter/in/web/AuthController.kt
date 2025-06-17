package com.cymply.auth.adapter.`in`.web

import com.cymply.auth.adapter.`in`.dto.TokenResponse
import com.cymply.auth.application.port.`in`.ReissueTokenCommand
import com.cymply.auth.application.port.`in`.ReissueTokenUseCase
import com.cymply.common.response.ApiResponse
import jakarta.annotation.security.RolesAllowed
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val reissueTokenUseCase: ReissueTokenUseCase
) : AuthApiController {
    override fun oAuth2Login(
        provider: String
    ) {
        throw UnsupportedOperationException("Delegate Security client")
    }

    @RolesAllowed("PENDING_USER")
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

    @RolesAllowed("USER")
    override fun refreshAccessToken(
        @AuthenticationPrincipal jwt: Jwt,
        refreshToken: String
    ): ApiResponse<TokenResponse> {
        TODO("Not yet implemented")
    }
}