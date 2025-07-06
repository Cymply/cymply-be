package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 작성 초대 링크 응답 DTO")
data class LetterCodeResponse(
    @field:Schema(description = "수신자 코드", example = "random")
    val code: String,

    @field:Schema(description = "편지 작성 폼", example = "https://www.cymply.kr/letters?code={code}")
    val link: String?
)