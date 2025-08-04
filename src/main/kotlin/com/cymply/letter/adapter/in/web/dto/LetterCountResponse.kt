package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 수신/발신 개수")
data class LetterCountResponse(

    @field:Schema(description = "편지 받은 개수", example = "10")
    val receivedCount: Long,

    @field:Schema(description = "보낸 편지 개수", example = "10")
    val sentCount: Long,
)
