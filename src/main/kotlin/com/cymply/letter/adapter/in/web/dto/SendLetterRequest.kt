package com.cymply.letter.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 전송 요청 DTO")
data class SendLetterRequest(
    @Schema(description = "수신자 코드", example = "1000")
    val recipientCode: String,

    @Schema(description = "편지 제목", example = "편지 제목")
    val title: String,

    @Schema(description = "편지 내용", example = "편지 내용입니다.")
    val content: String,

    @field:Schema(description = "음악 제목, 음악 검색에서 반환된 값", example = "좋은 날")
    val musicTitle: String,

    @field:Schema(description = "음악 아티스트, 음악 검색에서 반환된 값", example = "IU")
    val musicArtist: String
)
