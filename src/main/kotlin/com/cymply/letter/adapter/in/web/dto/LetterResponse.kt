package com.cymply.letter.adapter.`in`.web.dto

import com.cymply.music.adapter.`in`.web.dto.MusicResponse
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "편지 상세 응답 DTO")
data class LetterResponse(

    @field:Schema(description = "편지 ID", example = "1000")
    val id: Long,

    @field:Schema(description = "발신자 닉네임", example = "홍길동")
    val senderNickname: String,

    @field:Schema(description = "편지 내용", example = "안녕 테스터!")
    val content: String,

    @field:Schema(description = "전송 시각 (ISO 8601 형식)", example = "2025-06-01T12:00:00")
    val sentAt: LocalDateTime,

    @field:Schema(implementation = MusicResponse::class)
    val music: MusicResponse
)