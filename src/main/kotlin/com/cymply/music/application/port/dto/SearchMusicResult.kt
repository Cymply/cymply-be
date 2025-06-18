package com.cymply.music.application.port.dto

import com.cymply.music.domain.model.Music

data class SearchMusicResult(
    val title: String,
    val artist: String,
    val thumbnail: String?,
) {
    companion object {
        fun toResult(music: Music): SearchMusicResult =
            SearchMusicResult(
                title = music.title,
                artist = music.artist,
                thumbnail = null
            )
    }
}
