package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "닉네임 설정 요청 DTO")
data class SetLetterNicknameRequest(
    @Schema(description = "수신자 코드", example = "21자 랜덤 값")
    val recipientCode: String,

    @Schema(description = "닉네임", example = "홍길동")
    val nickname: String
)