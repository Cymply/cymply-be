package com.cymply.music.domain.model

class Music(
    val id: Long? = null,
    val title: String,
    val artist: String,
    val album: String?,
    val thumbnailUrl: String,
    val videoUrl: String? = null,
)
