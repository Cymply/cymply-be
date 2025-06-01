package com.cymply.music.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "음악 요청 DTO")
data class MusicRequest(

    @Schema(description = "음악 제목", example = "좋은 날")
    val title: String,

    @Schema(description = "음악 아티스트", example = "IU")
    val artist: String
)
