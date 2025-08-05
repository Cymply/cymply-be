package com.cymply.playlist.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "플레이리스트 생성 요청 DTO")
data class CreatePlaylistRequest(

    @field:Schema(description = "플레이리스트 제목", example = "J-POP 모음")
    val title: String
)
