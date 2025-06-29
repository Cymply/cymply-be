package com.cymply.music.application.port.dto

data class PlayMusicQuery(
    val title: String,
    val artist: String,
    val maxResults: Int = 1
)
