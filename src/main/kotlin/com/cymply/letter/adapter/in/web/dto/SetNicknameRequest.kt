package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "닉네임 설정 요청 DTO")
data class SetNicknameRequest(
    @Schema(description = "닉네임", example = "홍길동")
    val nickname: String
)