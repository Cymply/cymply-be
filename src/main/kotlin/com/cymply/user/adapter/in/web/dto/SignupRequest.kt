package com.cymply.user.adapter.`in`.web.dto

import com.cymply.common.model.Gender
import com.cymply.user.application.port.`in`.RegisterOAuth2UserCommand
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.oauth2.jwt.Jwt

@Schema(description = "OAuth2 회원가입 요청 DTO")
data class SignupRequest(
    @Schema(description = "닉네임", example = "Gildong", required = true)
    val nickname: String,

    @Schema(description = "성별(F: 여성, M: 남성)", example = "M", nullable = true)
    val gender: Gender?,

    @Schema(
        description = "연령대",
        example = "AGE_20_24",
        nullable = true,
        allowableValues = ["AGE_UNDER_10", "AGE_10_19", "AGE_20_24", "AGE_25_30", "AGE_OVER_30", "null"]
    )
    val ageRange: AgeRangeRequest?
) {
    companion object {
        fun from(principal: Jwt, request: SignupRequest) =
            RegisterOAuth2UserCommand(
                sub = principal.claims["sub"] as String,
                provider = principal.claims["provider"] as String,
                email = principal.claims["email"] as String,
                nickname = request.nickname,
                gender = request.gender,
                ageRange = request.ageRange?.name
            )
    }
}

