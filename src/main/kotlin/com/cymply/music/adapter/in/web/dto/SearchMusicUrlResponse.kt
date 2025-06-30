package com.cymply.music.adapter.`in`.web.dto

import com.cymply.music.application.port.dto.PlayMusicResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "음악 재생 링크 검색 응답 DTO")
data class SearchMusicUrlResponse(

    @field:Schema(description = "음악 재생 링크", example = "")
    val videoUrl: String
) {
    companion object {
        fun of(result: PlayMusicResult): SearchMusicUrlResponse =
            SearchMusicUrlResponse(
                videoUrl = result.videoUrl
            )
    }
}