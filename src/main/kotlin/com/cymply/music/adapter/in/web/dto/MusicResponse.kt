package com.cymply.music.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "음악 상세 응답 DTO")
data class MusicResponse(

    @field:Schema(description = "음악 ID", example = "1000")
    val id: Long,

    @field:Schema(description = "음악 제목", example = "좋은 날")
    val title: String,

    @field:Schema(description = "음악 아티스트", example = "IU")
    val artist: String,

    @field:Schema(description = "음악 썸네일 url", example = "www.example.com")
    val thumbnail: String,
)
