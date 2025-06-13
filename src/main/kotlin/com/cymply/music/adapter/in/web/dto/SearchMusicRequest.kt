package com.cymply.music.adapter.`in`.web.dto

import com.cymply.music.application.port.dto.SearchMusicQuery
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "음악 검색 요청 DTO")
data class SearchMusicRequest(

    @Schema(description = "검색 키워드", example = "좋은 날")
    val keyword: String,

    @Schema(description = "페이지 내 트랙 개수", example = "30")
    val limit: Int = 30,

    @Schema(description = "페이지 번호", example = "1")
    val page: Int = 1
) {
    fun toQuery(): SearchMusicQuery =
        SearchMusicQuery(
            keyword = keyword,
            limit = limit,
            page = page
        )
}