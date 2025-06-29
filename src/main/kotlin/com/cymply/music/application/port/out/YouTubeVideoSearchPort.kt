package com.cymply.music.application.port.out

interface YouTubeVideoSearchPort {
    fun searchVideoIds(query: String, maxResult: Long = 1L): List<String>
}