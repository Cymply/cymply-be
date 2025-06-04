package com.cymply.music.adapter.`in`.web.dto

data class MusicSearchRequest(
    val keyword: String,
    val type: MusicSearchType,
)

enum class MusicSearchType {
    TITLE, ARTIST
}