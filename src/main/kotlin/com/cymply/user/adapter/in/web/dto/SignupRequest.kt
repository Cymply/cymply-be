package com.cymply.user.adapter.`in`.web.dto

import com.cymply.common.model.Gender
import com.cymply.user.application.port.`in`.RegisterOAuth2UserCommand
import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.oauth2.jwt.Jwt
import java.time.LocalDate

@Schema(description = "OAuth2 회원가입 요청 DTO")
data class SignupRequest(
    @Schema(description = "닉네임", example = "Gildong", required = true)
    val nickname: String,
    @Schema(description = "이름", example = "홍길동", nullable = true)
    val name: String?,
    @Schema(description = "성별(F: 여성, M: 남성)", example = "M", nullable = true)
    val gender: Gender?,
    @Schema(description = "생년월일", example = "2000-01-01", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    val birth: LocalDate?
) {
    companion object {
        fun from(principal: Jwt, request: SignupRequest) =
            RegisterOAuth2UserCommand(
                email = principal.claims["email"] as String,
                sub = principal.claims["sub"] as String,
                provider = principal.claims["provider"] as String,
                nickname = request.nickname,
                name = request.name,
                gender = request.gender,
                birth = request.birth
            )
    }
}
