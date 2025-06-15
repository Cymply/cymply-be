package com.cymply.music.application.port.dto

data class SearchMusicQuery(
    val keyword: String,
    val limit: Int,
    val page: Int,
)
