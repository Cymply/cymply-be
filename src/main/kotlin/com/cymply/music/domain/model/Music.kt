package com.cymply.music.domain.model

data class Music(
    val title: String,
    val artist: String,
    val album: String?,
    val thumbnailUrl: String?
)
