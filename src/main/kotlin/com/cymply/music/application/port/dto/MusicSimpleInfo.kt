package com.cymply.music.application.port.dto

import com.cymply.music.domain.model.Music

data class MusicSimpleInfo(
    val id: Long,
    val title: String,
    val artist: String
) {
    companion object {
        fun from(music: Music): MusicSimpleInfo =
            MusicSimpleInfo(music.getIdOrThrow(), music.title, music.artist)
    }
}
