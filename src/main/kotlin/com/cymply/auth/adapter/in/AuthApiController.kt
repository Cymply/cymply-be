package com.cymply.auth.adapter.`in`

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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

    @Operation(summary = "Access Token 재발급", description = "Access Token 재발급을 실행합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "성공")
        ]
    )
    @GetMapping("/auth/refresh")
    fun issueAccessToken(
        @RequestBody refreshToken: String
    )
}
