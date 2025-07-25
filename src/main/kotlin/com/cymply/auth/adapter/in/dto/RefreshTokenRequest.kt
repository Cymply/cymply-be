package com.cymply.auth.adapter.`in`.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "AccessToken 재발급/RefreshToken 만료 요청 DTO")
data class RefreshTokenRequest(
    @Schema(description = "RefreshToken", required = true)
    val refreshToken: String
)