package com.cymply.auth.adapter.`in`.web

import com.cymply.common.response.ApiResponse
import jakarta.annotation.security.RolesAllowed
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
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
        TODO("Not yet implemented")
    }

    @RolesAllowed("USER")
    override fun refreshAccessToken(
        @AuthenticationPrincipal jwt: Jwt,
        refreshToken: String
    ): ApiResponse<TokenResponse> {
        TODO("Not yet implemented")
    }
}