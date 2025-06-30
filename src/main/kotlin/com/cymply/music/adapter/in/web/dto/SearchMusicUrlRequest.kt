package com.cymply.music.adapter.`in`.web.dto

import com.cymply.music.application.port.dto.PlayMusicQuery
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "음악 재생 링크 검색 요청 DTO")
data class SearchMusicUrlRequest(

    @field:Schema(description = "검색할 곡 제목", example = "Hello")
    val title: String,

    @field:Schema(description = "검색할 아티스트 이름", example = "Adele")
    val artist: String,

    @field:Schema(description = "최대 반환 개수", example = "1", minimum = "1")
    val maxResults: Int = 1
) {
    fun toQuery(): PlayMusicQuery =
        PlayMusicQuery(
            title = title,
            artist = artist,
            maxResults = maxResults
        )
}