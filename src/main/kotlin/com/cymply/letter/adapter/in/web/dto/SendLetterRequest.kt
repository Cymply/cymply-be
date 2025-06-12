package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 전송 요청 DTO")
data class SendLetterRequest(

    @field:Schema(description = "발신자 ID", example = "1000")
    val sender: Long,

    @Schema(description = "발신자 ID", example = "1000")
    val receipt: Long,

    @Schema(description = "편지 내용", example = "안녕 테스터!")
    val content: String
)