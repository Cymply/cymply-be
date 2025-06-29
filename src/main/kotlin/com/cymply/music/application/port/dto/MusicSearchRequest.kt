package com.cymply.music.application.port.dto

data class MusicSearchRequest(
    val keyword: String,
    val size: Int,
    val page: Int,
) {
    companion object {
        fun toRequest(query: SearchMusicQuery): MusicSearchRequest =
            MusicSearchRequest(
                keyword = query.keyword,
                size = query.size,
                page = query.page,
            )
    }
}
