package com.cymply.music.adapter.`in`.web.dto

import com.cymply.music.application.port.dto.SearchMusicResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "음악 검색 응답 DTO")
data class SearchMusicResponse(

    @field:Schema(description = "음악 제목", example = "좋은 날")
    val title: String,

    @field:Schema(description = "음악 아티스트", example = "IU")
    val artist: String,

    @field:Schema(description = "음악 썸네일 url", example = "www.example.com")
    val thumbnailUrl: String?,
) {
    companion object {
        fun from(result: SearchMusicResult): SearchMusicResponse =
            SearchMusicResponse(
                title = result.title,
                artist = result.artist,
                thumbnailUrl = result.thumbnailUrl
            )
    }
}
