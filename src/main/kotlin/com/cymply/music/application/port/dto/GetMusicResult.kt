package com.cymply.music.application.port.dto

import com.cymply.music.domain.model.Music

data class GetMusicResult(
    val id: Long,
    val title: String,
    val artist: String,
    val thumbnailUrl: String,
    val videoUrl: String
) {
    companion object {
        fun from(music: Music): GetMusicResult =
            GetMusicResult(
                id = music.getIdOrThrow(),
                title = music.title,
                artist = music.artist,
                thumbnailUrl = music.thumbnailUrl,
                videoUrl = music.videoUrl!!
            )
    }
}
