package com.cymply.music.application.port.dto

data class LastFmSearchRequest(
    val keyword: String,
    val limit: Int,
    val page: Int,
) {
    companion object {
        fun toRequest(query: SearchMusicQuery): LastFmSearchRequest =
            LastFmSearchRequest(
                keyword = query.keyword,
                limit = query.limit,
                page = query.page,
            )
    }
}
