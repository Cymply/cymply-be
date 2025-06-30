package com.cymply.music.application.port.dto

data class SearchMusicQuery(
    val keyword: String,
    val size: Int,
    val page: Int,
)
