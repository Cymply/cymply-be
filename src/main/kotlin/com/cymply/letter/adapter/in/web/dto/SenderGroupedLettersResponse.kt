package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "받은 편지 목록 그룹화 응답 DTO")
data class SenderGroupedLettersResponse(

    @field:Schema(description = "발신자 ID", example = "1000")
    val senderId: Long,

    @field:Schema(description = "발신자 닉네임", example = "홍길동")
    val senderNickname: String,

    @field:Schema(description = "특정 사용자에게서 받은 편지 목록")
    val letters: List<LetterSummaryResponse>
)

@Schema(description = "편지 요약 응답 DTO")
data class LetterSummaryResponse(

    @field:Schema(description = "편지 ID", example = "1000")
    val letterId: Long,

    @field:Schema(description = "편지 요약 내용", example = "안녕 테스터!")
    val title: String,

    @field:Schema(implementation = MusicResponse::class)
    val music: MusicResponse
)
