package com.cymply.auth.adapter.`in`.web

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
interface AuthApiController {
    @Operation(summary = "OAuth2 로그인 요청", description = "OAuth2 소셜 로그인을 실행합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "302", description = "로그인 페이지로 리다이렉트됩니다.")
        ]
    )
    @GetMapping("/oauth2/authorization/{provider}")
    fun oAuth2Login(
        @Parameter(
            name = "provider",
            `in` = ParameterIn.PATH,
            description = "소셜 플랫폼명",
            required = true,
            schema = Schema(type = "string", allowableValues = ["google", "kakao"])
        )
        @PathVariable provider: String
    )

    @Operation(summary = "회원가입 후 Access Token 재발급", description = "가입 완료 후 정상적인 Access Token 발급을 실행합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200")
        ]
    )
    @GetMapping("/api/v1/users/signup/oauth2/success")
    fun oAuth2SignupSuccess(
        @AuthenticationPrincipal jwt: Jwt
    ): com.cymply.common.response.ApiResponse<TokenResponse>

    @Operation(summary = "Access Token 재발급", description = "만료된 Access Token 재발급을 실행합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "성공")
        ]
    )
    @GetMapping("/api/v1/auth/refresh")
    fun refreshAccessToken(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody refreshToken: String
    ): com.cymply.common.response.ApiResponse<TokenResponse>
}
