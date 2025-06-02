package com.cymply.playlist.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "플레이리스트 생성 응답 DTO")
data class CreatePlaylistResponse(

    @field:Schema(description = "플레이리스트 ID", example = "1000")
    val id: Long,

    @field:Schema(description = "플레이리스트 제목", example = "J-POP 모음")
    val title: String
)
